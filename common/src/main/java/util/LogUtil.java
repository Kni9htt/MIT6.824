package util;

import java.util.Date;

public class LogUtil {
    public static void log(Object... args) {
        String now = new Date().toString();
        StringBuffer sb = new StringBuffer();
        for (Object arg : args) {
            sb.append(arg);
        }
        String log = now + "-" + sb;
        FileUtil.append("E:\\Projects\\JAVA_IDEA\\MIT6.5840\\logs\\log.log", log);
        System.out.println(log);
    }
}
