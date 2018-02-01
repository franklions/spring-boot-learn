package spring.boot.learn.grpc.demo.helloworld;


import io.grpc.Server;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslProvider;
import spring.boot.learn.grpc.demo.protocol.GreeterGrpc;
import spring.boot.learn.grpc.demo.protocol.HelloReply;
import spring.boot.learn.grpc.demo.protocol.HelloRequest;

import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executors;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/15
 * @since Jdk 1.8
 */
public class HelloWorldServer {
    private int port = 50051;
    private Server server;

    public static final int MAX_MESSAGE_SIZE = 16 * 1024 * 1024;

    private void start() throws IOException, CertificateException {
        X509Certificate[] serverTrustedCaCerts = {
                loadX509Cert("ca.pem")
        };
        server = NettyServerBuilder.forPort(port)
                .executor(Executors.newFixedThreadPool(16))
                .maxMessageSize(MAX_MESSAGE_SIZE)
                .sslContext(GrpcSslContexts
                        .forServer(loadCert("server1.pem"),loadCert("server1.key"))
                        .trustManager(serverTrustedCaCerts)
                        .clientAuth(ClientAuth.REQUIRE)
                        .sslProvider(SslProvider.OPENSSL)
                        .build()
                ).addService(new GreeterImpl())
                .build()
                .start();
        System.out.println("service start.....");

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloWorldServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop(){
        if(server != null){
            server.shutdown();
        }
    }

    // block 一直到退出程序
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, CertificateException {
        final HelloWorldServer server = new HelloWorldServer();
        server.start();
        server.blockUntilShutdown();
    }

    // 实现 定义一个实现服务接口的类
    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            System.out.println("service:"+req.getName());
            HelloReply reply = HelloReply.newBuilder().setMessage(("Hello: " + req.getName())).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
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
