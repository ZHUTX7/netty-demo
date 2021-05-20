package NettyTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/5/10 15:40
 */
public class ChannelFutureTest {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new StringEncoder());
                    }
                }).connect(new InetSocketAddress("localhost",6666));
        //2.1 方法一，同步等待
        //connect方法是异步非阻塞的，主线程把连接任务交给子线程去干，所以，
        //如果不调用sync方法，channelFuture会是个空对象
//        channelFuture.sync();//阻塞住 ,直到连接建立完成
//        Channel channel = channelFuture.channel();
//        channel.writeAndFlush("hello , i'm client ");

        //2.2 方法二，异步等待
        //使用addListener(回调对象)方法异步处理结果
        channelFuture.addListener( new ChannelFutureListener(){
            //在 客户端与服务器连接完成后， 异步调用该方法
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                channel.writeAndFlush("hello ,is me");
            }
        }) ;

    }
}
