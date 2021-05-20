package NettyTest;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;

import java.util.concurrent.ExecutionException;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/5/10 17:44
 */
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EventLoop eventloop = new NioEventLoopGroup().next();

        //1.主动创建promise对象
        DefaultPromise<Integer> promise = new DefaultPromise<Integer>(eventloop);

        new Thread(()->{
            System.out.println("开始计算");
            try{
                Thread.sleep(4000);
                promise.setSuccess(6666/0);
            }catch (Exception e){
                e.printStackTrace();
                promise.setFailure(e);
            }

        }).start();

        System.out.printf("结果是",promise.get());

        //promise作为一个线程之间传递数据的容器


    }
}
