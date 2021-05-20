package NettyTest.EventLoopTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/5/10 14:22
 */
public class EventLoopServer {
    public static void main(String[] args) {
        //细分2：创建一个独立的EventLoopGroup
        EventLoopGroup group = new DefaultEventLoop();

        new ServerBootstrap()
                .group(new NioEventLoopGroup(),new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                         //addLast里可以指定线程处理
                       nioSocketChannel.pipeline().addLast(new StringDecoder());
//                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//                            @Override
//                            public void channelRead(ChannelHandlerContext ctx,Object msg){
//                                String m = "changed";
//                                msg = (Object) m;
//                                System.out.printf((String)msg);
//                            }
//                        });
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx,Object msg){
                                System.out.printf((String)msg);
                            }
                        });
                    }
                }).bind(6666);
    }
}
