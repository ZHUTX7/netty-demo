package JavaNioServer.nioTest.block;


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
 * @description :
 * @update : 2021/4/26 18:09
 */
public class MyServer {
    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//16个字节
        //1.创建Server
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //2.绑定监听端口
        ssc.bind(new InetSocketAddress(6666));

        //3.创建连接集合
        List<SocketChannel> channels  = new ArrayList<>();

        while(true){
            //4.等待建立连接 阻塞
            System.out.println("等待连接ing...");
            SocketChannel sc = ssc.accept();
            System.out.println("连接上啦！");
            channels.add(sc);

            for (SocketChannel channel:channels){
                //从channel读 ，往byteBuffer里面写
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                if (!(channel.read(byteBuffer)>0)) break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // 切换成读模式
                            byteBuffer.flip();
                            System.out.println("客户端数据："+new String(byteBuffer.array(),0,byteBuffer.limit()));

                            byteBuffer.clear();

                        }
                    }
                }).start();

            }
        }
    }


}
