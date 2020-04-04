FROM gradle:6.3-jdk8 as builder

COPY . /usr/src/app
USER root
RUN chown -R gradle:gradle /usr/src/app
USER gradle

WORKDIR /usr/src/app
RUN gradle assemble

FROM adoptopenjdk/openjdk8-openj9:alpine-slim
WORKDIR /root/
COPY --from=builder /usr/src/app/build/libs/user-manager-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 8090
CMD java $JAVA_OPTS -Xshareclasses -Xquickstart -jar "./app.jar"