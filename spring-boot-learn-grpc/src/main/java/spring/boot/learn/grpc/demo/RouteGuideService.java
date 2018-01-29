package spring.boot.learn.grpc.demo;

import io.grpc.stub.StreamObserver;
import spring.boot.learn.grpc.demo.routguide.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @author flsh
 * @version 1.0
 * @description
 * @date 2018/1/18
 * @since Jdk 1.8
 */
public class RouteGuideService extends RouteGuideGrpc.RouteGuideImplBase {

    private Logger logger = Logger.getLogger(RouteGuideService.class.getName());

    private Collection<Feature> features = new ArrayList<Feature>(){{
        add(Feature.newBuilder().setName("one").setLocation(
                Point.newBuilder().setLatitude(100).setLongitude(200)).build());
        add(Feature.newBuilder().setName("tow").setLocation(
                Point.newBuilder().setLatitude(200).setLongitude(300).build()).build());
        }};

    private final ConcurrentMap<Point, List<RouteNote>> routeNotes =
            new ConcurrentHashMap<Point, List<RouteNote>>();

    @Override
    public void getFeature(Point request, StreamObserver<Feature> responseObserver) {
        responseObserver.onNext(checkFeature(request));
        responseObserver.onCompleted();

    }

    @Override
    public void listFeatures(Rectangle request, StreamObserver<Feature> responseObserver) {
        int left = min(request.getLo().getLongitude(), request.getHi().getLongitude());
        int right = max(request.getLo().getLongitude(), request.getHi().getLongitude());
        int top = max(request.getLo().getLatitude(), request.getHi().getLatitude());
        int bottom = min(request.getLo().getLatitude(), request.getHi().getLatitude());

        //循环发送多个feature给客户端
        for (Feature feature : features) {
            if (!isExists(feature)) {
                continue;
            }

            responseObserver.onNext(feature);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            int lat = feature.getLocation().getLatitude();
//            int lon = feature.getLocation().getLongitude();
//            if (lon >= left && lon <= right && lat >= bottom && lat <= top) {
//                responseObserver.onNext(feature);
//            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Point> recordRoute(StreamObserver<RouteSummary> responseObserver) {
        return new StreamObserver<Point>() {
            int pointCount;
            int featureCount;
            int distance;
            Point previous;
            long startTime = System.nanoTime();

            @Override
            public void onNext(Point point) {
                pointCount++;
                if (checkFeature(point) != null) {
                    featureCount++;
                }
                // For each point after the first, add the incremental distance from the previous point
                // to the total distance value.
                if (previous != null) {
                    distance += 1;
                }
                previous = point;
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "Encountered error in recordRoute", t);
            }

            @Override
            public void onCompleted() {
                long seconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
                responseObserver.onNext(RouteSummary.newBuilder().setPointCount(pointCount)
                        .setFeatureCount(featureCount).setDistance(distance)
                        .setElapsedTime((int) seconds).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<RouteNote> routeChat(StreamObserver<RouteNote> responseObserver) {
        return new StreamObserver<RouteNote>() {
            @Override
            public void onNext(RouteNote note) {
                List<RouteNote> notes = getOrCreateNotes(note.getLocation());

                // Respond with all previous notes at this location.
                for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
                    responseObserver.onNext(prevNote);
                }

                // Now add the new note to the list
                notes.add(note);
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "routeChat cancelled");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * Get the notes list for the given location. If missing, create it.
     */
    private List<RouteNote> getOrCreateNotes(Point location) {
        List<RouteNote> notes = Collections.synchronizedList(new ArrayList<RouteNote>());
        List<RouteNote> prevNotes = routeNotes.putIfAbsent(location, notes);
        return prevNotes != null ? prevNotes : notes;
    }

    private boolean isExists(Feature feature) {
        return true;
    }

    private Feature checkFeature(Point location) {

        for (Feature feature : features) {
            if (feature.getLocation().getLatitude() == location.getLatitude()
                    && feature.getLocation().getLongitude() == location.getLongitude()) {
                return feature;
            }
        }
        return Feature.newBuilder().setName("new").setLocation(location).build();
    }
}
