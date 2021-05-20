package JavaNioServer.file;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author : ztx
 * @version :V1.0
 * @description : 文件输出流
 * @update : 2021/4/26 15:14
 */
public class FileChannelTest {
    public static void main(String[] args) {
      //  transfer2();
        copyFile();
    }

    //一次传输
    public static void transferAll(){
        try{
            File file;
            FileChannel from  = new FileInputStream("data.txt").getChannel();
            FileChannel to = new FileOutputStream("copy.txt").getChannel();
            //这句话 会利用底层操作系统的零拷贝进行优化
            from.transferTo(0,from.size(),to);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    public static void transfer2(){
        try{
            File file;
            FileChannel from  = new FileInputStream("data.txt").getChannel();
            FileChannel to = new FileOutputStream("copy.txt").getChannel();
            //这句话 会利用底层操作系统的零拷贝进行优化
            long size = from.size();
            for (long left = size;left>0 ;){
                System.out.println("position is :"+(size-left));
                left -= from.transferTo((size - left) ,left,to);
            }
            from.transferTo(0,from.size(),to);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  static void copyFile()  {
        Path source = Paths.get("data.txt");
        Path copy = Paths.get("copy.txt");
        try{
            Files.copy(source,copy, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
