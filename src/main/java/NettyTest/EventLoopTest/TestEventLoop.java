package NettyTest.EventLoopTest;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author : ztx
 * @version :V1.0
 * @description :  Event提交任务
 * @update : 2021/5/10 14:11
 */

public class TestEventLoop {
    public static void main(String[] args) {
        //1. 创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup(5);
        //2. 获取下一个事件循环对象
        System.out.println(group.next().toString());
        //3. 执行普通任务
        group.next().submit(()->{
            try{
                Thread.sleep(1000);
            }catch(Exception e){
                System.out.printf("...");
            }
            System.out.println("ok");
        });
        System.out.println("main");
    }
}
