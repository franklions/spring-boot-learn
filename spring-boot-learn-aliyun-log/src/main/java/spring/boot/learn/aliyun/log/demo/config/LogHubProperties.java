package spring.boot.learn.aliyun.log.demo.config;

import com.aliyun.openservices.loghub.client.config.LogHubCursorPosition;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2017/11/6
 * @since Jdk 1.8
 */
@ConfigurationProperties("aliyun.loghub")
public class LogHubProperties {
    //worker 默认的拉取数据的时间间隔
    public static final long DEFAULT_DATA_FETCH_INTERVAL_MS = 200;
    //consumer group 的名字，不能为空，支持 [a-z][0-9] 和'_'，'-'，长度为3~63字符，只能以小写字母和数字开头结尾
    private String mConsumerGroupName;
    //consumer 的名字，必须确保同一个 consumer group 下面的各个 consumer 不重名
    private String mWorkerInstanceName;
    //loghub 数据接口地址
    private String mLogHubEndPoint;
    //项目名称
    private String mProject;
    //日志库名称
    private String mLogStore;
    //云账号的 access key id
    private String mAccessId;
    //云账号的 access key
    private String mAccessKey;
    //用于指出在服务端没有记录Shard的 checkpoint 的情况下应该从什么位置消费Shard，如果服务端保存了有效的 checkpoint 信息，那么这些取值不起任何作用， mCursorPosition 取值可以是 [BEGIN_CURSOR, END_CURSOR, SPECIAL_TIMER_CURSOR]中的一个，BEGIN_CURSOR 表示从Shard中的第一条数据开始消费，END_CURSOR 表示从Shard中的当前时刻的最后一条数据开始消费，SPECIAL_TIMER_CURSOR 和下面的 mLoghubCursorStartTime 配对使用，表示从特定的时刻开始消费数据。
    private LogHubCursorPosition mCursorPosition;
    //当 mCursorPosition 取值为 SPECIAL_TIMER_CURSOR 时，指定消费时间，单位是秒。
    private int  mLoghubCursorStartTime ;
    // 轮询获取 LogHub 数据的时间间隔，间隔越小，抓取越快，单位是毫秒，默认是 DEFAULT_DATA_FETCH_INTERVAL_MS，建议时间间隔 200ms 以上。
    private long mDataFetchIntervalMillis;
    // worker 向服务端汇报心跳的时间间隔，单位是毫秒，建议取值 10000ms。
    private long mHeartBeatIntervalMillis;
    //是否按序消费
    private boolean mConsumeInOrder;

    public String getmConsumerGroupName() {
        return mConsumerGroupName;
    }

    public void setmConsumerGroupName(String mConsumerGroupName) {
        this.mConsumerGroupName = mConsumerGroupName;
    }

    public String getmWorkerInstanceName() {
        return mWorkerInstanceName;
    }

    public void setmWorkerInstanceName(String mWorkerInstanceName) {
        this.mWorkerInstanceName = mWorkerInstanceName;
    }

    public String getmLogHubEndPoint() {
        return mLogHubEndPoint;
    }

    public void setmLogHubEndPoint(String mLogHubEndPoint) {
        this.mLogHubEndPoint = mLogHubEndPoint;
    }

    public String getmProject() {
        return mProject;
    }

    public void setmProject(String mProject) {
        this.mProject = mProject;
    }

    public String getmLogStore() {
        return mLogStore;
    }

    public void setmLogStore(String mLogStore) {
        this.mLogStore = mLogStore;
    }

    public String getmAccessId() {
        return mAccessId;
    }

    public void setmAccessId(String mAccessId) {
        this.mAccessId = mAccessId;
    }

    public String getmAccessKey() {
        return mAccessKey;
    }

    public void setmAccessKey(String mAccessKey) {
        this.mAccessKey = mAccessKey;
    }

    public LogHubCursorPosition getmCursorPosition() {
        return mCursorPosition;
    }

    public void setmCursorPosition(LogHubCursorPosition mCursorPosition) {
        this.mCursorPosition = mCursorPosition;
    }

    public int getmLoghubCursorStartTime() {
        return mLoghubCursorStartTime;
    }

    public void setmLoghubCursorStartTime(int mLoghubCursorStartTime) {
        this.mLoghubCursorStartTime = mLoghubCursorStartTime;
    }

    public long getmDataFetchIntervalMillis() {
        return mDataFetchIntervalMillis;
    }

    public void setmDataFetchIntervalMillis(long mDataFetchIntervalMillis) {
        this.mDataFetchIntervalMillis = mDataFetchIntervalMillis;
    }

    public long getmHeartBeatIntervalMillis() {
        return mHeartBeatIntervalMillis;
    }

    public void setmHeartBeatIntervalMillis(long mHeartBeatIntervalMillis) {
        this.mHeartBeatIntervalMillis = mHeartBeatIntervalMillis;
    }

    public boolean ismConsumeInOrder() {
        return mConsumeInOrder;
    }

    public void setmConsumeInOrder(boolean mConsumeInOrder) {
        this.mConsumeInOrder = mConsumeInOrder;
    }
}
