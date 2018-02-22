package spring.boot.learn.grpc.client.demo;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import spring.boot.learn.grpc.client.demo.protocol.GreeterGrpc;
import spring.boot.learn.grpc.client.demo.protocol.HelloReply;
import spring.boot.learn.grpc.client.demo.protocol.HelloRequest;


import javax.annotation.Nullable;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/2/12
 * @since Jdk 1.8
 */
public class HelloWorldClient {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final GreeterGrpc.GreeterFutureStub futureStub;
    private final GreeterGrpc.GreeterStub stub;


    public HelloWorldClient(String host,int port) throws IOException {
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext(true)
                .build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
        futureStub = GreeterGrpc.newFutureStub(channel);
        stub = GreeterGrpc.newStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public  void greet(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response = blockingStub.sayHello(request);
        System.out.println(response.getMessage());

    }

    public void greetFuture(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        ListenableFuture<HelloReply> futureRes = futureStub.sayHello(request);

//        try {
//            HelloReply response = futureRes.get();
//            System.out.println(response.getMessage());
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        Futures.addCallback(futureRes, new FutureCallback<HelloReply>() {
            @Override
            public void onSuccess(@Nullable HelloReply result) {
                System.out.println(result.getMessage());
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void asyncGreet(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();

        StreamObserver<HelloReply> response  = new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {
                System.out.println(value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("server send completed!");
            }
        };

        stub.sayHello( request,response);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
       HelloWorldClient client = new HelloWorldClient("127.0.0.1", 50051);
        client.asyncGreet(Thread.currentThread().getName() +"_world_async:\t" + UUID.randomUUID().toString());
        System.out.println("async greet finish");
        client.greetFuture(Thread.currentThread().getName() +"_world_future:\t" + UUID.randomUUID().toString());
        System.out.println("future finish");
        client.greet(Thread.currentThread().getName() +"_world:\t" + UUID.randomUUID().toString());
        System.out.println("greet finish");
        System.out.println("main finish");
        Thread.sleep(5000);
        client.shutdown();
    }

}
