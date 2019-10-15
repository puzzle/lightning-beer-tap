FROM fabric8/java-centos-openjdk8-jdk

MAINTAINER Thomas Philipona <philipona@puzzle.ch>

ENV WSB_URL "ws://localhost:8080/websocket/invoice?access_token="
ENV WSB_TOPIC "/topic/invoice"
ENV WSB_COMMAND "./dummy_command.sh"
ENV WSB_PREFIX "beerTap"

EXPOSE 8080 9090

USER root

RUN yum install -y curl

USER jboss

RUN mkdir -p /tmp/src/
COPY src /tmp/src/src
COPY gradle /tmp/src/gradle
COPY build.gradle gradlew settings.gradle webhook.sh /tmp/src/

RUN cd /tmp/src && sh gradlew build

RUN cp -a /tmp/src/build/libs/*.jar /deployments/websocket-bridge.jar && cp -a /tmp/src/webhook.sh /deployments/webhook.sh