package JavaNioServer.file;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/4/26 15:51
 */
public class FileDirWalkTest {

    public static void main(String[] args) throws IOException {
        walk();
    }

    public static void walk() throws IOException {
        final AtomicInteger dirCount = new AtomicInteger();
        final AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("F:\\Program Files\\Git"),
                new SimpleFileVisitor<Path>(){
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        System.out.println("=====>"+dir);
                        dirCount.incrementAndGet();
                        return super.preVisitDirectory(dir, attrs);
                    }
                    @Override
                    public FileVisitResult visitFile(Path file,BasicFileAttributes attrs) throws IOException {
                        System.out.println(file);
                        fileCount.incrementAndGet();
                        return super.visitFile(file,attrs);
                    }
                });
        // dir数量不算主目录需要-1
        System.out.println("dir count: "+dirCount);
        System.out.println("file count: "+fileCount);
    }
}
