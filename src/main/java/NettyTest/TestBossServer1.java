package NettyTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * 多核CPU下，单线程无法充分利用CPU
 *
 * Boss负责接待连接， Worker负责读写
 * woker应该是有限的, 一个worker可以对应多个socketChannel
 * 其实相比与之前,只是增加了一个多路复用器用来管理连接
 * 但问题在于,连接这个channel是可以复制的?
 * @update : 2021/4/29 15:39
 */
public class TestBossServer1 {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("Boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss,0,null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(6666));
        //1.创建固定数量的worker 并初始化
        Worker worker =  new Worker("worker-0");
        worker.register();
        while (true){
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();
                //监听读事件
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    sc.register(worker.worker,SelectionKey.OP_READ,null);
                }
            }
        }
    }


   //职责：专门检测读写事件
   static class Worker implements Runnable{
        private Thread thread;
        private Selector worker;
        private String name;
        private volatile boolean flag = false;
        public Worker(String name){
            this.name = name;
        }

        //初始化线程和selector
        //1 - worker -1 -thread
        public void register() throws IOException {
           if(!flag){
               this.thread = new Thread(this,name);
               this.thread.start();
               this.worker = Selector.open();
               flag = true;
           }
        }

        @Override
        public void run() {
            while(true){
                try{
                    worker.select();
                    Iterator<SelectionKey> iter2 = worker.selectedKeys().iterator();
                    while (iter2.hasNext()){
                        SelectionKey key = iter2.next();
                        iter2.remove();
                        //如果是读事件
                        if(key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            //这个channel是复制过来的吗
                            SocketChannel channel = (SocketChannel) key.channel();
                            channel.read(buffer);
                            buffer.flip();
                            String msg = new String(buffer.array(),0, buffer.limit());
                            System.out.printf(msg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

