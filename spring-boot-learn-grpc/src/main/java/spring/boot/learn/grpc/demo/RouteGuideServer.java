package spring.boot.learn.grpc.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.NettyServerBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/18
 * @since Jdk 1.8
 */
public class RouteGuideServer {
    private static final Logger logger = Logger.getLogger(RouteGuideServer.class.getName());

    private final int port;
    private final Server server;


    public RouteGuideServer(int port) {
        this.port = port;
        this.server = NettyServerBuilder.forAddress(new InetSocketAddress("192.168.12.177",this.port))
                .addService(new RouteGuideService()).build();
    }

    /**
     * Main method.  This comment makes the linter happy.
     */
    public static void main(String[] args) throws Exception {
        RouteGuideServer server = new RouteGuideServer(8980);
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException {
        server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may has been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                RouteGuideServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    /** Stop serving requests and shutdown resources. */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
