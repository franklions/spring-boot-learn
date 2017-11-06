package spring.boot.learn.aliyun.log.demo.AliyunService;

import com.aliyun.openservices.log.common.*;
import com.aliyun.openservices.loghub.client.ILogHubCheckPointTracker;
import com.aliyun.openservices.loghub.client.exceptions.LogHubCheckPointException;
import com.aliyun.openservices.loghub.client.interfaces.ILogHubProcessor;

import java.util.List;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/11/6
 * @since Jdk 1.8
 */
public class IotAppLogHubProecessor implements ILogHubProcessor {

    private int mShardId;
    // 记录上次持久化 check point 的时间
    private long mLastCheckTime = 0;

    @Override
    public void initialize(int shardId) {
        this.mShardId = shardId;
    }

    @Override
    public String process(List<LogGroupData> logGroups, ILogHubCheckPointTracker checkPointTracker) {
        for(LogGroupData logGroup: logGroups){
            FastLogGroup flg = logGroup.GetFastLogGroup();
            System.out.println(String.format("\tcategory\t:\t%s\n\tsource\t:\t%s\n\ttopic\t:\t%s\n\tmachineUUID\t:\t%s",
                    flg.getCategory(), flg.getSource(), flg.getTopic(), flg.getMachineUUID()));
            System.out.println("Tags");
            for (int tagIdx = 0; tagIdx < flg.getLogTagsCount(); ++tagIdx) {
                FastLogTag logtag = flg.getLogTags(tagIdx);
                System.out.println(String.format("\t%s\t:\t%s", logtag.getKey(), logtag.getValue()));
            }
            for (int lIdx = 0; lIdx < flg.getLogsCount(); ++lIdx) {
                FastLog log = flg.getLogs(lIdx);
                System.out.println("--------\nLog: " + lIdx + ", time: " + log.getTime() + ", GetContentCount: " + log.getContentsCount());
                for (int cIdx = 0; cIdx < log.getContentsCount(); ++cIdx) {
                    FastLogContent content = log.getContents(cIdx);
                    System.out.println(content.getKey() + "\t:\t" + content.getValue());
                }
            }
        }
        long curTime = System.currentTimeMillis();
        // 每隔 60 秒，写一次 check point 到服务端，如果 60 秒内，worker crash，
        // 新启动的 worker 会从上一个 checkpoint 其消费数据，有可能有重复数据
        if (curTime - mLastCheckTime >  60 * 1000)
        {
            try
            {
                //参数 true 表示立即将 checkpoint 更新到服务端，为 false 会将 checkpoint 缓存在本地，默认隔 60s
                //后台会将 checkpoint 刷新到服务端。
                checkPointTracker.saveCheckPoint(true);
            }
            catch (LogHubCheckPointException e)
            {
                e.printStackTrace();
            }
            mLastCheckTime = curTime;
        }
        else
        {
            try
            {
                checkPointTracker.saveCheckPoint(false);
            }
            catch (LogHubCheckPointException e)
            {
                e.printStackTrace();
            }
        }
        // 返回空表示正常处理数据， 如果需要回滚到上个 check point 的点进行重试的话，可以 return checkPointTracker.getCheckpoint()
        return null;
    }

    @Override
    public void shutdown(ILogHubCheckPointTracker checkPointTracker) {
        //将消费断点保存到服务端。
        try {
            checkPointTracker.saveCheckPoint(true);
        } catch (LogHubCheckPointException e) {
            e.printStackTrace();
        }
    }
}
