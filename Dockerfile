FROM openjdk:8
COPY . /usr/src/work
WORKDIR /usr/src/work
RUN mkdir /usr/src/work/out
RUN javac -cp src src/EcosystemSimulator.java -d out
RUN ls out
ENTRYPOINT ["java", "-cp", "out", "EcosystemSimulator"]
