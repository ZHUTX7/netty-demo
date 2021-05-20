package JavaNioServer.nioTest.nonBlock;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : ztx
 * @version :V1.0
 * @description : 非阻塞模式 - 没有用到selector , 单线程在没有连接，没有数据流
 *                的情况下也会不断循环，造成资源浪费
 * @update : 2021/4/26 18:09
 */
public class MyServer {
    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//16个字节
        //1.创建Server
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //添加非阻塞代码
        ssc.configureBlocking(false);

        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(6666));

        //3.创建连接集合
        List<SocketChannel> channels  = new ArrayList<>();

        while(true){
            //4.等待建立连接 阻塞
//            System.out.println("等待连接ing...");

            //非阻塞下 ，线程会继续执行，  ssc.accept()返回的是null
            SocketChannel sc = ssc.accept();


            if(sc ==null){
//                System.out.println(" socketChannel is null ");
//                System.out.println(" 线程继续执行 ");
            }
            else{
                //这里还要设置一下非阻塞模式
                sc.configureBlocking(false);
                channels.add(sc);
            }
            for (SocketChannel channel:channels){
                //System.out.println("开始循环channels");
                if ((channel.read(byteBuffer)>0)) {
                    // 切换成读模式
                    byteBuffer.flip();
                    System.out.println("客户端数据：" + new String(byteBuffer.array(), 0, byteBuffer.limit()));

                    byteBuffer.clear();
                }

            }
        }
    }


}
