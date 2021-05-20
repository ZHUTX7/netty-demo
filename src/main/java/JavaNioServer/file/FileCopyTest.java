package JavaNioServer.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author : ztx
 * @version :V1.0
 * @description :
 * @update : 2021/4/26 17:53
 */
public class FileCopyTest {
    public static void main(String[] args) throws IOException {
        copyFile();
    }
    public static void copyFile() throws IOException {
        String source = "F:\\Program Files\\feiq\\Db";
        String target = "F:\\Program Files\\feiq\\Db\\copy";

        Files.walk(Paths.get(source)).forEach(path -> {
            String targetName = path.toString().replace(source,target);
            if(Files.isDirectory(path)){
                try {
                    Files.createDirectories(Paths.get(target));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(Files.isRegularFile(path)){
                try {
                    Files.copy(path,Paths.get(targetName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
