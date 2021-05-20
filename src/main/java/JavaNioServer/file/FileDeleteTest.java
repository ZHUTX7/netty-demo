package JavaNioServer.file;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/4/26 17:14
 */
public class FileDeleteTest {

    public static void main(String[] args) throws IOException {
        deleteFile();
    }

    public static void deleteFile() throws IOException {
        String path = "";
        Files.walkFileTree(Paths.get(path),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                System.out.println(file);
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                 return super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("退出<======"+dir);
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });

    }
}
