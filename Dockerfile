FROM openjdk:8
COPY ./src  /usr/src/work
COPY ./data  /usr/src/work/data
WORKDIR /usr/src/work
RUN javac EcosystemSimulator.java
ENTRYPOINT ["java","EcosystemSimulator"]
