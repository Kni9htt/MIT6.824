import common.Cons;
import common.MRArg;
import common.MRTask;
import rpc.io.RpcNode;
import util.LogUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;

public class Master extends RpcNode {
    private List<String> files;
    private Integer reduceNum;
    private Map<Integer, MRTask> portTask = new HashMap<>();
    private ConcurrentLinkedDeque<MRTask> tasks = new ConcurrentLinkedDeque<>();
    private ConcurrentLinkedDeque<MRTask> reduceTasks = new ConcurrentLinkedDeque<>();
    private Set<String> reduceOutFiles = new HashSet<>();
    private Set<Integer> doneMapTasks = new HashSet<>();
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public Master(List<String> files, Integer reduceNum) {
        this.files = files;
        this.reduceNum = reduceNum;
        for (int i = 0; i < files.size(); ++i) {
            String file = files.get(i);
            tasks.offer(new MRTask(i, Cons.TASK_TYPE_MAP, Cons.TASK_STATUS_TODO, file, reduceNum,
                    files.size(), ""));
        }
        for (int i = 0; i < reduceNum; ++i) {
            reduceTasks.offer(new MRTask(i, Cons.TASK_TYPE_REDUCE, Cons.TASK_STATUS_TODO, "", reduceNum,
                    files.size(), CommonFile.reduceOutFile(i)));
        }
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void run() throws Exception {
        serve();
        checkWorkers();
    }

    /**
     * 每隔一段时间进行检查，是否有任务失败
     *
     */
    private void checkWorkers() {
        Runnable runnable = () -> {
          while (true) {
              Integer errPort = null;
              for (Integer port : portTask.keySet()) {
                  Object ret = call(port, "rpcPing", new Object[]{port + ""});
                  if (ret == null) {
                      LogUtil.log("ok to find fail worker on port," +port + "task:" + portTask.get(port));
                      errPort = port;
                      break;
                  }
                  try {
                      Thread.sleep(1000L);
                  } catch (Exception e) {
                  }
                  if (errPort != null) {
                      MRTask mrTask = portTask.get(errPort);
                      if (mrTask.getTaskType().equals(Cons.TASK_TYPE_MAP)) {
                          tasks.offer(mrTask);
                      } else {
                          reduceTasks.offer(mrTask);
                      }
                      portTask.remove(errPort);
                  }
                  try {
                      Thread.sleep(1000L);
                  } catch (Exception e) {
                  }
              }
          }
        };
        new Thread(runnable).start();
    }

    /**
     * 指派任务
     *
     * @return
     */
    public synchronized MRArg requireTask(Integer port) {
        if (tasks.isEmpty()) {
            return MRArg.empty(allFinished());
        }
        MRTask mrTask = tasks.poll();
        return newMRTask(mrTask, port);
    }

    /**
     * 任务完成
     *
     * @param workerId
     * @param taskType
     * @return
     */
    public synchronized MRArg doneTask(Integer workerId, final Integer taskType, Integer port) {
        if (taskType.equals(Cons.TASK_TYPE_MAP)) {
            doneMapTasks.add(workerId);
        }
        if (taskType.equals(Cons.TASK_TYPE_REDUCE)) {
            reduceOutFiles.add(CommonFile.reduceOutFile(workerId));
        }
        if (!tasks.isEmpty()) {
            MRTask mrTask = tasks.poll();
            return newMRTask(mrTask, port);
        }
        Boolean mapDone = doneMapTasks.size() == files.size();
        if (!mapDone) {
            return MRArg.empty(false);
        }
        if (!reduceTasks.isEmpty()) {
            MRTask mrTask = reduceTasks.poll();
            return newMRTask(mrTask, port);
        }
        return MRArg.empty(allFinished());
    }

    /**
     * 新建newMRArg
     */
    private MRArg newMRTask(MRTask mrTask, Integer port) {
        portTask.put(port, mrTask);
        return new MRArg(mrTask);
    }


    /**
     * 判断是否所有任务均已完成
     */
    private synchronized Boolean allFinished() {
        Boolean done = this.reduceOutFiles.size() == this.reduceNum;
        if (done) {
            countDownLatch.countDown();
        }
        return done;
    }

}
