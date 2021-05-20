package JavaNioServer.nioTest.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/4/26 20:13
 */
public class MyClient2 {
    public static void main(String[] args) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String msg = "hello";
        byteBuffer.put(msg.getBytes());
        byteBuffer.flip();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",6666));
        socketChannel.write(byteBuffer);
        byteBuffer.clear();

        System.out.println("start client");
        while (true){
            Scanner sc =new Scanner(System.in);
            msg = "客户端B"+sc.next();
            byteBuffer.put(msg.getBytes());
            //切换到读模式
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();

        }

    }
}
