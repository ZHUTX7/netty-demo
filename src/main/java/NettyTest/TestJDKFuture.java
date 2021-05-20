package NettyTest;

import java.util.concurrent.*;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/5/10 17:28
 */
public class TestJDKFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.线程池
        ExecutorService service = Executors.newFixedThreadPool(5);
        //2.提交任务
        Future<String> future =  service.submit(new Callable<String>(){
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "success";
            }
        });
        //3. 主线程通过future来获取结果
        System.out.println(future.get());;
    }

}
