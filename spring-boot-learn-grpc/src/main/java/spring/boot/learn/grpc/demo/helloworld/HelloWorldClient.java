package spring.boot.learn.grpc.demo.helloworld;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.netty.handler.ssl.SslProvider;
import spring.boot.learn.grpc.demo.protocol.GreeterGrpc;
import spring.boot.learn.grpc.demo.protocol.HelloReply;
import spring.boot.learn.grpc.demo.protocol.HelloRequest;

import javax.annotation.Nullable;
import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/15
 * @since Jdk 1.8
 */
public class HelloWorldClient {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private final GreeterGrpc.GreeterFutureStub futureStub;
    private final GreeterGrpc.GreeterStub stub;


    public HelloWorldClient(String host,int port) throws IOException, CertificateException {
//        channel = ManagedChannelBuilder.forAddress(host,port)
//                .usePlaintext(true)
//                .build();
        X509Certificate[] clientTrustedCaCerts = {
                loadX509Cert("ca.pem")
        };
        channel = NettyChannelBuilder.forAddress(host, port)
//                .overrideAuthority("foo.test.google.fr")
                .negotiationType(NegotiationType.TLS)
                .executor(Executors.newFixedThreadPool(1))
                .sslContext(GrpcSslContexts
                        .forClient()
                        .keyManager(loadCert("client.pem"), loadCert("client.key"))
                        .trustManager(clientTrustedCaCerts)
                        .sslProvider(SslProvider.OPENSSL)
                        .build())
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

    public static void main(String[] args) throws InterruptedException, IOException, CertificateException {
        HelloWorldClient client = new HelloWorldClient("foo.test.google.fr", 50051);

        client.asyncGreet(Thread.currentThread().getName() +"_world_async:\t" + UUID.randomUUID().toString());
        System.out.println("async greet finish");
        client.greetFuture(Thread.currentThread().getName() +"_world_future:\t" + UUID.randomUUID().toString());
        System.out.println("future finish");
        client.greet(Thread.currentThread().getName() +"_world:\t" + UUID.randomUUID().toString());
        System.out.println("greet finish");
//        for(int i=0;i<100;i++) {
//            Thread thread = new Thread(){
//                @Override
//                public void run() {
//                    for (int i = 0; i < 5; i++) {
//                        client.greet(Thread.currentThread().getName() +"_world:\t" + UUID.randomUUID().toString());
//                    }
//                }
//            };
//
//            thread.start();
//        }
        System.out.println("main finish");
         Thread.sleep(5000);
        client.shutdown();
    }

    public static X509Certificate loadX509Cert(String fileName)
            throws CertificateException, IOException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        InputStream in = HelloWorldServer.class.getResourceAsStream("/certs/" + fileName);
        try {
            return (X509Certificate) cf.generateCertificate(in);
        } finally {
            in.close();
        }
    }
    private   File loadCert(String name) throws IOException {
        InputStream
                in = new BufferedInputStream(HelloWorldServer.class.getResourceAsStream("/certs/" + name));
        File tmpFile = File.createTempFile(name, "");
        tmpFile.deleteOnExit();

        OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile));
        try {
            int b;
            while ((b = in.read()) != -1) {
                os.write(b);
            }
            os.flush();
        } finally {
            in.close();
            os.close();
        }

        return tmpFile;
    }
}
