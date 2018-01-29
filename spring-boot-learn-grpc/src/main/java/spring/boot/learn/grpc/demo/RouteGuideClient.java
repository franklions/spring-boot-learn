package spring.boot.learn.grpc.demo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import spring.boot.learn.grpc.demo.routguide.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/22
 * @since Jdk 1.8
 */
public class RouteGuideClient {

    private static final Logger logger = Logger.getLogger(RouteGuideClient.class.getName());

    private final ManagedChannel channel;
    private final RouteGuideGrpc.RouteGuideBlockingStub blockingStub;
    private final RouteGuideGrpc.RouteGuideStub asyncStub;

    public RouteGuideClient(String localhost, int port) {
        this(ManagedChannelBuilder.forAddress(localhost,port).usePlaintext(true));
    }

    public RouteGuideClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = RouteGuideGrpc.newBlockingStub(channel);
        asyncStub = RouteGuideGrpc.newStub(channel);
    }

    public static void main(String[] args) throws InterruptedException {
         List<Feature> features = new ArrayList<Feature>(){{
            add(Feature.newBuilder().setName("c-three").setLocation(
                    Point.newBuilder().setLatitude(1000).setLongitude(2000)).build());
            add(Feature.newBuilder().setName("c-fuse").setLocation(
                    Point.newBuilder().setLatitude(2000).setLongitude(3000).build()).build());
        }};

        RouteGuideClient client = new RouteGuideClient("192.168.12.177",8980);

        try {
            client.getFeature(100,200);

            client.listFeature(1,2,3,4);

            client.getFeature(0,0);

            client.recordRoute(features,2);

            CountDownLatch finishLatch = client.routeChat();

            if(!finishLatch.await(1,TimeUnit.MINUTES)){
                System.out.println("routeChat can not finish within 1 minutes");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            client.shutdown();
        }
    }

    private CountDownLatch routeChat() {

        CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<RouteNote> responseObserver = new StreamObserver<RouteNote>() {
            @Override
            public void onNext(RouteNote note) {
                info("Got message \"{0}\" at {1}, {2}", note.getMessage(), note.getLocation()
                        .getLatitude(), note.getLocation().getLongitude());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        StreamObserver<RouteNote> requestObserver =
                asyncStub.routeChat(responseObserver);

        try {
            RouteNote[] requests =
                    {newNote("First message", 0, 0), newNote("Second message", 0, 1),
                            newNote("Third message", 1, 0), newNote("Fourth message", 1, 1)};

            for (RouteNote request : requests) {
                info("Sending message \"{0}\" at {1}, {2}", request.getMessage(), request.getLocation()
                        .getLatitude(), request.getLocation().getLongitude());
                requestObserver.onNext(request);
            }
        } catch (RuntimeException e) {
            // Cancel RPC
            requestObserver.onError(e);
            throw e;
        }
        // Mark the end of requests
        requestObserver.onCompleted();

        return finishLatch;
    }

    private void getFeature(int lat, int lon) {
        Point request = Point.newBuilder().setLatitude(lat).setLongitude(lon).build();

        Feature feature = blockingStub.getFeature(request);

        info("Found feature called \"{0}\" at {1}, {2}",
                feature.getName(),
                feature.getLocation().getLatitude(),
                feature.getLocation().getLongitude());
    }

    private void listFeature(int lowLat, int lowLon, int hiLat, int hiLon){
        Rectangle request = Rectangle.newBuilder()
                .setHi(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
                .setLo(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
        Iterator<Feature> features;
        try {
            features = blockingStub.listFeatures(request);
            for (int i = 1; features.hasNext(); i++) {
                Feature feature = features.next();
                info("Result #" + i + ": {0}", feature);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void recordRoute(List<Feature> features, int numPoints){
        StreamObserver<RouteSummary> responseObserver = new StreamObserver<RouteSummary>() {
            @Override
            public void onNext(RouteSummary summary) {
                info("Finished trip with {0} points. Passed {1} features. "
                                + "Travelled {2} meters. It took {3} seconds.", summary.getPointCount(),
                        summary.getFeatureCount(), summary.getDistance(), summary.getElapsedTime());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                info("on completed");
            }
        };

        StreamObserver<Point> requestObserver = asyncStub.recordRoute(responseObserver);

        try {
            for(Feature feature :features){
                requestObserver.onNext(feature.getLocation());
            }

        }catch (RuntimeException ex){
            ex.printStackTrace();
            requestObserver.onError(ex);
        }

        requestObserver.onCompleted();
    }

    private void info(String msg, Object... params) {
        logger.log(Level.INFO, msg, params);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    private RouteNote newNote(String message, int lat, int lon) {
        return RouteNote.newBuilder().setMessage(message)
                .setLocation(Point.newBuilder().setLatitude(lat).setLongitude(lon).build()).build();
    }
}
