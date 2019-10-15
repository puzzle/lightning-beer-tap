FROM fabric8/java-centos-openjdk8-jdk

MAINTAINER Thomas Philipona <philipona@puzzle.ch>

EXPOSE 9050

USER root

RUN yum install -y curl
RUN yum install -y epel-release && yum install -y tor

USER toranon

CMD tor