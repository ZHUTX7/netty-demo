package NettyTest;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/5/10 17:36
 */
public class TestNettyFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup group = new  NioEventLoopGroup();
        EventLoop eventLoop = group.next();
        Future<String> future =  eventLoop.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                System.out.println("正在获取结果");
                return "success";
            }
        });
        //1.同步
//        System.out.printf("获取结果中...");
//        System.out.printf("结果是"+future.get());


        //异步获取
        future.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                System.out.println("方法2，结果："+future.get());
            }
        });
    }
}
