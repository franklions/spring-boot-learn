package spring.boot.learn.grpc.demo.routguide;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.9.0)",
    comments = "Source: rout_guide.proto")
public final class RouteGuideGrpc {

  private RouteGuideGrpc() {}

  public static final String SERVICE_NAME = "spring.boot.learn.grpc.demo.RouteGuide";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getGetFeatureMethod()} instead.
  public static final io.grpc.MethodDescriptor<Point,
      Feature> METHOD_GET_FEATURE = getGetFeatureMethod();

  private static volatile io.grpc.MethodDescriptor<Point,
      Feature> getGetFeatureMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<Point,
      Feature> getGetFeatureMethod() {
    io.grpc.MethodDescriptor<Point, Feature> getGetFeatureMethod;
    if ((getGetFeatureMethod = RouteGuideGrpc.getGetFeatureMethod) == null) {
      synchronized (RouteGuideGrpc.class) {
        if ((getGetFeatureMethod = RouteGuideGrpc.getGetFeatureMethod) == null) {
          RouteGuideGrpc.getGetFeatureMethod = getGetFeatureMethod = 
              io.grpc.MethodDescriptor.<Point, Feature>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "spring.boot.learn.grpc.demo.RouteGuide", "GetFeature"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Point.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Feature.getDefaultInstance()))
                  .setSchemaDescriptor(new RouteGuideMethodDescriptorSupplier("GetFeature"))
                  .build();
          }
        }
     }
     return getGetFeatureMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getListFeaturesMethod()} instead.
  public static final io.grpc.MethodDescriptor<Rectangle,
      Feature> METHOD_LIST_FEATURES = getListFeaturesMethod();

  private static volatile io.grpc.MethodDescriptor<Rectangle,
      Feature> getListFeaturesMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<Rectangle,
      Feature> getListFeaturesMethod() {
    io.grpc.MethodDescriptor<Rectangle, Feature> getListFeaturesMethod;
    if ((getListFeaturesMethod = RouteGuideGrpc.getListFeaturesMethod) == null) {
      synchronized (RouteGuideGrpc.class) {
        if ((getListFeaturesMethod = RouteGuideGrpc.getListFeaturesMethod) == null) {
          RouteGuideGrpc.getListFeaturesMethod = getListFeaturesMethod = 
              io.grpc.MethodDescriptor.<Rectangle, Feature>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "spring.boot.learn.grpc.demo.RouteGuide", "ListFeatures"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Rectangle.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Feature.getDefaultInstance()))
                  .setSchemaDescriptor(new RouteGuideMethodDescriptorSupplier("ListFeatures"))
                  .build();
          }
        }
     }
     return getListFeaturesMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getRecordRouteMethod()} instead.
  public static final io.grpc.MethodDescriptor<Point,
      spring.boot.learn.grpc.demo.routguide.RouteSummary> METHOD_RECORD_ROUTE = getRecordRouteMethod();

  private static volatile io.grpc.MethodDescriptor<Point,
      spring.boot.learn.grpc.demo.routguide.RouteSummary> getRecordRouteMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<Point,
      spring.boot.learn.grpc.demo.routguide.RouteSummary> getRecordRouteMethod() {
    io.grpc.MethodDescriptor<Point, spring.boot.learn.grpc.demo.routguide.RouteSummary> getRecordRouteMethod;
    if ((getRecordRouteMethod = RouteGuideGrpc.getRecordRouteMethod) == null) {
      synchronized (RouteGuideGrpc.class) {
        if ((getRecordRouteMethod = RouteGuideGrpc.getRecordRouteMethod) == null) {
          RouteGuideGrpc.getRecordRouteMethod = getRecordRouteMethod = 
              io.grpc.MethodDescriptor.<Point, spring.boot.learn.grpc.demo.routguide.RouteSummary>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "spring.boot.learn.grpc.demo.RouteGuide", "RecordRoute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Point.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  spring.boot.learn.grpc.demo.routguide.RouteSummary.getDefaultInstance()))
                  .setSchemaDescriptor(new RouteGuideMethodDescriptorSupplier("RecordRoute"))
                  .build();
          }
        }
     }
     return getRecordRouteMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getRouteChatMethod()} instead.
  public static final io.grpc.MethodDescriptor<spring.boot.learn.grpc.demo.routguide.RouteNote,
      spring.boot.learn.grpc.demo.routguide.RouteNote> METHOD_ROUTE_CHAT = getRouteChatMethod();

  private static volatile io.grpc.MethodDescriptor<spring.boot.learn.grpc.demo.routguide.RouteNote,
      spring.boot.learn.grpc.demo.routguide.RouteNote> getRouteChatMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<spring.boot.learn.grpc.demo.routguide.RouteNote,
      spring.boot.learn.grpc.demo.routguide.RouteNote> getRouteChatMethod() {
    io.grpc.MethodDescriptor<spring.boot.learn.grpc.demo.routguide.RouteNote, spring.boot.learn.grpc.demo.routguide.RouteNote> getRouteChatMethod;
    if ((getRouteChatMethod = RouteGuideGrpc.getRouteChatMethod) == null) {
      synchronized (RouteGuideGrpc.class) {
        if ((getRouteChatMethod = RouteGuideGrpc.getRouteChatMethod) == null) {
          RouteGuideGrpc.getRouteChatMethod = getRouteChatMethod = 
              io.grpc.MethodDescriptor.<spring.boot.learn.grpc.demo.routguide.RouteNote, spring.boot.learn.grpc.demo.routguide.RouteNote>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "spring.boot.learn.grpc.demo.RouteGuide", "RouteChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  spring.boot.learn.grpc.demo.routguide.RouteNote.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  spring.boot.learn.grpc.demo.routguide.RouteNote.getDefaultInstance()))
                  .setSchemaDescriptor(new RouteGuideMethodDescriptorSupplier("RouteChat"))
                  .build();
          }
        }
     }
     return getRouteChatMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RouteGuideStub newStub(io.grpc.Channel channel) {
    return new RouteGuideStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RouteGuideBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RouteGuideBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RouteGuideFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RouteGuideFutureStub(channel);
  }

  /**
   */
  public static abstract class RouteGuideImplBase implements io.grpc.BindableService {

    /**
     */
    public void getFeature(Point request,
        io.grpc.stub.StreamObserver<Feature> responseObserver) {
      asyncUnimplementedUnaryCall(getGetFeatureMethod(), responseObserver);
    }

    /**
     */
    public void listFeatures(Rectangle request,
        io.grpc.stub.StreamObserver<Feature> responseObserver) {
      asyncUnimplementedUnaryCall(getListFeaturesMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Point> recordRoute(
        io.grpc.stub.StreamObserver<spring.boot.learn.grpc.demo.routguide.RouteSummary> responseObserver) {
      return asyncUnimplementedStreamingCall(getRecordRouteMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<spring.boot.learn.grpc.demo.routguide.RouteNote> routeChat(
        io.grpc.stub.StreamObserver<spring.boot.learn.grpc.demo.routguide.RouteNote> responseObserver) {
      return asyncUnimplementedStreamingCall(getRouteChatMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetFeatureMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Point,
                Feature>(
                  this, METHODID_GET_FEATURE)))
          .addMethod(
            getListFeaturesMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                Rectangle,
                Feature>(
                  this, METHODID_LIST_FEATURES)))
          .addMethod(
            getRecordRouteMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                Point,
                spring.boot.learn.grpc.demo.routguide.RouteSummary>(
                  this, METHODID_RECORD_ROUTE)))
          .addMethod(
            getRouteChatMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                spring.boot.learn.grpc.demo.routguide.RouteNote,
                spring.boot.learn.grpc.demo.routguide.RouteNote>(
                  this, METHODID_ROUTE_CHAT)))
          .build();
    }
  }

  /**
   */
  public static final class RouteGuideStub extends io.grpc.stub.AbstractStub<RouteGuideStub> {
    private RouteGuideStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RouteGuideStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RouteGuideStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RouteGuideStub(channel, callOptions);
    }

    /**
     */
    public void getFeature(Point request,
        io.grpc.stub.StreamObserver<Feature> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetFeatureMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listFeatures(Rectangle request,
        io.grpc.stub.StreamObserver<Feature> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getListFeaturesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Point> recordRoute(
        io.grpc.stub.StreamObserver<spring.boot.learn.grpc.demo.routguide.RouteSummary> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getRecordRouteMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<spring.boot.learn.grpc.demo.routguide.RouteNote> routeChat(
        io.grpc.stub.StreamObserver<spring.boot.learn.grpc.demo.routguide.RouteNote> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getRouteChatMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class RouteGuideBlockingStub extends io.grpc.stub.AbstractStub<RouteGuideBlockingStub> {
    private RouteGuideBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RouteGuideBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RouteGuideBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RouteGuideBlockingStub(channel, callOptions);
    }

    /**
     */
    public Feature getFeature(Point request) {
      return blockingUnaryCall(
          getChannel(), getGetFeatureMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<Feature> listFeatures(
        Rectangle request) {
      return blockingServerStreamingCall(
          getChannel(), getListFeaturesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RouteGuideFutureStub extends io.grpc.stub.AbstractStub<RouteGuideFutureStub> {
    private RouteGuideFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RouteGuideFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RouteGuideFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RouteGuideFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Feature> getFeature(
        Point request) {
      return futureUnaryCall(
          getChannel().newCall(getGetFeatureMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_FEATURE = 0;
  private static final int METHODID_LIST_FEATURES = 1;
  private static final int METHODID_RECORD_ROUTE = 2;
  private static final int METHODID_ROUTE_CHAT = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RouteGuideImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RouteGuideImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_FEATURE:
          serviceImpl.getFeature((Point) request,
              (io.grpc.stub.StreamObserver<Feature>) responseObserver);
          break;
        case METHODID_LIST_FEATURES:
          serviceImpl.listFeatures((Rectangle) request,
              (io.grpc.stub.StreamObserver<Feature>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RECORD_ROUTE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.recordRoute(
              (io.grpc.stub.StreamObserver<spring.boot.learn.grpc.demo.routguide.RouteSummary>) responseObserver);
        case METHODID_ROUTE_CHAT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.routeChat(
              (io.grpc.stub.StreamObserver<spring.boot.learn.grpc.demo.routguide.RouteNote>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class RouteGuideBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RouteGuideBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return spring.boot.learn.grpc.demo.routguide.RouteGuideProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RouteGuide");
    }
  }

  private static final class RouteGuideFileDescriptorSupplier
      extends RouteGuideBaseDescriptorSupplier {
    RouteGuideFileDescriptorSupplier() {}
  }

  private static final class RouteGuideMethodDescriptorSupplier
      extends RouteGuideBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    RouteGuideMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RouteGuideGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RouteGuideFileDescriptorSupplier())
              .addMethod(getGetFeatureMethod())
              .addMethod(getListFeaturesMethod())
              .addMethod(getRecordRouteMethod())
              .addMethod(getRouteChatMethod())
              .build();
        }
      }
    }
    return result;
  }
}
