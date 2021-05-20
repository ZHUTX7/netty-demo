package nettyHttpServer;

import nettyHttpServer.initializer.HttpServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @author : ztx
 * @version :V1.0
 * @description : http服务器 - 测试
 * @update : 2021/4/25 16:13
 */
public class ServerZ {

    private int port ;

    public ServerZ(int port){
        this.port = port;
    }

    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(boss, worker)
        .handler(new LoggingHandler(LogLevel.DEBUG))
        .channel(NioServerSocketChannel.class)
        .childHandler(new HttpServerInitializer());

        ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(port)).sync();
        System.out.println("port is "+port);
        channelFuture.channel().closeFuture().sync();
    }

    public static void main(String[] args) throws InterruptedException {
        new ServerZ(8080).start();
    }


}
