package NettyTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/5/10 16:35
 */
public class CloseChannelTest {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group =  new NioEventLoopGroup();
        Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new StringEncoder());
                    }
                }).connect(new InetSocketAddress("localhost",6666))
                .sync().channel();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.printf("channel is :"+channel.isActive());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        new Thread(()->{
            Scanner sc = new Scanner(System.in);
            while (true) {

                String msg = sc.nextLine();
                if ("quit".equals(msg)) {
                    //close()也是一个异步方法
                    channel.close();
                    System.out.println("执行到print真的已经关闭了吗？");
                    break;
                }
                channel.writeAndFlush(msg);
            }
        },"input").start();

        ChannelFuture closeFuture =channel.closeFuture();
//        //真正关闭流程 方法1:
//
//        closeFuture.sync();
//        System.out.println("这才真正关闭了");

        // 2.方法2 回调，监听器
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                System.out.printf("这才真正关闭了");
                //此时，通道虽然关闭了，但是group里面还有一些线程再跑
                //可以调用shutdownGracefully（）,优雅关闭
                group.shutdownGracefully();
            }
        });

    }
}
