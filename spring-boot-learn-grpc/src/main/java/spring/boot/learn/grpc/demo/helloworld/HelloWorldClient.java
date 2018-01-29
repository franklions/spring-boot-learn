package spring.boot.learn.grpc.demo.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslProvider;
import spring.boot.learn.grpc.demo.protocol.GreeterGrpc;
import spring.boot.learn.grpc.demo.protocol.HelloReply;
import spring.boot.learn.grpc.demo.protocol.HelloRequest;

import javax.net.ssl.SSLException;
import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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


    public HelloWorldClient(String host,int port) throws IOException, CertificateException {
//        channel = ManagedChannelBuilder.forAddress(host,port)
//                .usePlaintext(true)
//                .build();
        X509Certificate[] clientTrustedCaCerts = {
                loadX509Cert("ca.pem")
        };
        channel = NettyChannelBuilder.forAddress(host, port)
                .overrideAuthority("foo.test.google.fr")
                .negotiationType(NegotiationType.TLS)
                .sslContext(GrpcSslContexts
                        .forClient()
                        .keyManager(loadCert("client.pem"), loadCert("client.key"))
                        .trustManager(clientTrustedCaCerts)
                        .sslProvider(SslProvider.OPENSSL)
                        .build())
                .build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public  void greet(String name){
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response = blockingStub.sayHello(request);
        System.out.println(response.getMessage());

    }

    public static void main(String[] args) throws InterruptedException, IOException, CertificateException {
        HelloWorldClient client = new HelloWorldClient("localhost", 50051);
        client.greet(Thread.currentThread().getName() +"_world:\t" + UUID.randomUUID().toString());

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
        Thread.sleep(5000);
        System.in.read();
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
