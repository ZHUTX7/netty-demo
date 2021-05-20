package NettyTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author : ztx
 * @version :V1.0
 * @description : netty是一个异步的，基于事件驱动的网络应用框架
 * 用于快速开发可维护，高性能的网络服务器和客户端。
 * Trustin Lee 韩国人
 * @update : 2021/5/10 10:48
 */
public class NettyServer {
    public static void main(String[] args) {
        //1. 启动器,组装netty组件，启动服务器
        new ServerBootstrap()
                //2. BossEventLoop , WorkerEventLoop ,group租
                .group(new NioEventLoopGroup()) //accept事件 ，内部封装好了，处理连接
                .channel(NioServerSocketChannel.class)
                .childHandler(
                        //channel代表和客户端进行数据读写的通道Initialize初始化
                        new ChannelInitializer<NioSocketChannel>() {
                            //连接建立后才调用initChannel方法
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());

                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ch,Object msg) throws Exception{
                                System.out.println(msg);
                            }
                        });
                    }
                }).bind(6666);
    }
}
