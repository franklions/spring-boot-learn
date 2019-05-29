FROM java:8-jre-alpine

ADD target/aiar-callback-example.jar /usr/local/micro-service/

EXPOSE 8080

WORKDIR /usr/local/micro-service/

CMD ["/usr/bin/java","-jar","-Xms512m","-Xmx900m","aiar-callback-example.jar"]
