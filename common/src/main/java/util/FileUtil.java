package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class FileUtil {
    private static final String KEEP_FILE = ".keep";
    
    public static String readFile(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            LogUtil.log("fail to read file," +  file);
            return null;
        }
    }


    public static void append(String path, String cnt) {
        String toAppend = cnt + "\n";
        try {
            Files.write(Paths.get(path),
                    toAppend.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        }catch (Exception e) {
            LogUtil.log("fail to write file," + path + "err: " + e);
        }
    }

    public static void write(String path, String cnt) {
        try {
            Files.write(Paths.get(path),
                    cnt.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE);
        }catch (Exception e) {
            LogUtil.log("fail to write file," + e);
        }
    }

    public static Stream<String> stream(String file) {
        try {
            return Files.lines(Paths.get(file));
        }catch (Exception e) {
            return null;
        }
    }
}
