// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rout_guide.proto

package spring.boot.learn.grpc.demo.routguide;

public interface FeatureDatabaseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:spring.boot.learn.grpc.demo.FeatureDatabase)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .spring.boot.learn.grpc.demo.Feature feature = 1;</code>
   */
  java.util.List<Feature>
      getFeatureList();
  /**
   * <code>repeated .spring.boot.learn.grpc.demo.Feature feature = 1;</code>
   */
  Feature getFeature(int index);
  /**
   * <code>repeated .spring.boot.learn.grpc.demo.Feature feature = 1;</code>
   */
  int getFeatureCount();
  /**
   * <code>repeated .spring.boot.learn.grpc.demo.Feature feature = 1;</code>
   */
  java.util.List<? extends spring.boot.learn.grpc.demo.routguide.FeatureOrBuilder> 
      getFeatureOrBuilderList();
  /**
   * <code>repeated .spring.boot.learn.grpc.demo.Feature feature = 1;</code>
   */
  spring.boot.learn.grpc.demo.routguide.FeatureOrBuilder getFeatureOrBuilder(
          int index);
}
