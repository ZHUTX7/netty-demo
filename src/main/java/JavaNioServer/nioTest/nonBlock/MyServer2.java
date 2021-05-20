package JavaNioServer.nioTest.nonBlock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author : ztx
 * @version :V1.0
 * @description : 异步非阻塞NIO ， 使用了Selector
 * @update : 2021/4/28 11:55
 */

public class MyServer2  {
    public static void main(String[] args) throws IOException {

        //1.创建Selector , 管理多个channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        ssc.bind(new InetSocketAddress(6666));

        //2.创建SelectionKey与channel之间的联系（注册）
        SelectionKey sscKey = ssc.register(selector, 0,null);
        //3.key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);


        while (true){
            //3. select方法，没有事件发生，线程阻塞
            //有事件发生 ，线程恢复运行
            //在事件未处理时，select()不会阻塞，时间发生后，要么处理:（逻辑代码）要么取消:key.cancel()
            System.out.println("调用select()方法 ， 查看未处理事件");
            System.out.println("当前未处理时间有： "+ selector.select()+" 件");

            //4.拿到可读可写可连接 事件集合B
            //selector.selectedKeys()返回一个set集合
            selector.selectedKeys();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()){
                System.out.println("hasNext()方法 ，处理事件");
                SelectionKey key = iter.next();

                //5.区分事件

                //连接事件
                if(key.isAcceptable()){
                    System.out.println("发现连接事件，处理中....");
                    //通过key拿到触发的channel
                    ServerSocketChannel channel   =  (ServerSocketChannel)key.channel();
                    SocketChannel socketChannel =  channel.accept();
                    socketChannel.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    SelectionKey scKey =   socketChannel.register(selector,0,buffer);
                    scKey.interestOps(SelectionKey.OP_READ);

                    selector.selectedKeys().remove(key);
                    System.out.println("处理完毕，移除key....");
                }
                //读事件
                else if(key.isReadable()){
                    try{
                        System.out.println("发现读事件，处理中....");
                        SocketChannel channel   =  (SocketChannel)key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read =  channel.read(buffer);

                        if(read == -1){
                            key.cancel();
                        }
                        else {
                            buffer.flip();
                            System.out.println("客户端数据：" + new String(buffer.array(), 0, buffer.limit()));
                            buffer.clear();
                            selector.selectedKeys().remove(key);
                            System.out.println("处理完毕，移除key....");
                        }

                    }catch(IOException e){
                       // e.printStackTrace();
                        System.out.println("客户端主动关闭， 移除key...");
                        //当客户端主动关闭， 会调用该方法 移除key
                        key.cancel();
                    }
                }




            }
        }
    }
}
