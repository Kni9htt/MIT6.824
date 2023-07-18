import common.KeyValue;
import func.MapFunc;
import func.ReduceFunc;

import java.util.*;
import java.io.File;

public class MapReduceDemon {
    public static void main(String[] args) {
        Distributed distributed = new Distributed();
        MapFuncImpl mapFunc = new MapFuncImpl();
        ReduceFuncImpl reduceFunc = new ReduceFuncImpl();
        String filesPath = "E:\\Projects\\JAVA_IDEA\\MIT6.5840\\mapreduce\\src\\main\\resources\\articles";
        List<String> paths = getAllFile(filesPath, false);


        try {
            distributed.run(mapFunc, reduceFunc, paths, 8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAllFile(String directoryPath,boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if(isAddDirectory){
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllFile(file.getAbsolutePath(),isAddDirectory));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

    static class MapFuncImpl implements MapFunc {

        @Override
        public List<KeyValue> mapF(String file, String cnt) {
            Map<String, String> values = new HashMap<>();
            cnt = cnt.replaceAll("\r|\n", " ");
            cnt = cnt.replaceAll("\\p{Punct}", " ");
            cnt = cnt.replaceAll("\\d+", " ");
            String[] words = cnt.split(" ");
            for (int i = 0; i < words.length; ++i) {
                if (words[i].equals("")) continue;
                if (!values.containsKey(words[i])) {
                    values.put(words[i], "1");
                } else {
                    values.put(words[i], values.get(words[i]) + ",1");
                }
            }

            List<KeyValue> keyValues = new ArrayList<>();
            for (String key : values.keySet()) {
                KeyValue keyValue = new KeyValue(key, values.get(key));
                keyValues.add(keyValue);
            }
            return keyValues;
        }
    }

    static class ReduceFuncImpl implements ReduceFunc {

        @Override
        public String reduceF(String key, List<String> values) {
            int sum = 0;
            for (int i = 0; i < values.size(); ++i) {
                sum += values.get(i).length() / 2 + 1;
            }

            return "" + sum;
        }
    }
}
