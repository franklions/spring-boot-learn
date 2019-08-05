package sping.boot.learn.rocketmq.producer;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author administrator
 * @version 1.0
 * @date 2019-08-02
 * @since Jdk 1.8
 */
public class TransactionListenerImpl implements TransactionListener {
    private AtomicInteger transactionIndex = new AtomicInteger(0);

    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();


    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        System.out.println("执行本地事务msg = " + msg.toString());
        System.out.println("执行本地事务arg = " + arg);
        System.out.println(new Date());

        try {
            System.out.println(Thread.currentThread().getName());

            int value = transactionIndex.getAndIncrement();
            int status = value % 3;
            localTrans.put(msg.getTransactionId(), status);
            //DB操作 应该带上事务 service -> dao
            //如果数据操作失败  需要回滚    同时返回RocketMQ一条失败消息  意味着消费者无法消费到这条失败的消息
            //如果成功 就要返回一条rocketMQ成功的消息，意味着消费者将读取到这条消息
            //o就是attachment
            String tags = msg.getTags();
            if (tags.equals("TagB")) {
                System.out.println("===> 本地事务执行失败，进行MQ ROLLBACK");
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }


            if (tags.equals("TagD")) {
                System.out.println("===> 本地事务执行未知结果，进行UNKNOW");
                return LocalTransactionState.UNKNOW;
            }

            if (tags.equals("TagE")) {
                System.out.println("===> 本地事务出现异常，抛出异常信息");
                throw new IllegalArgumentException("本地事务执行失败");
            }

            ((LocalTransExecuteResult)arg).setValue(msg.getKeys());
        }catch (Exception ex){
            System.out.println("===> 本地事务出现异常,打印异常信息");
            ex.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }

        System.out.println("===> 本地事务执行成功，发送确认消息");
        // return LocalTransactionState.UNKNOW;
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * broke配置消息检查时间
     * 多长时间进行检查
     * transactionTimeOut 	默认配置是6000 但从现实情况来看默认是1分钟
     * transactionCheckMax 15 次
     * @param msg
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("未决事务，服务器回查客户端msg =" + msg.toString());
        System.out.println(new Date());
        //由于RocketMQ迟迟没有收到消息的确认消息，因此主动询问这条prepare消息，是否正常？
        //可以查询数据库看这条数据是否已经处理
        Integer status = localTrans.get(msg.getTransactionId());
        if (null != status) {
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
