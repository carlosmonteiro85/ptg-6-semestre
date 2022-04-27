FROM openjdk:11

EXPOSE 8080

ADD target/carana-virus.jar carana-virus.jar

COPY data  /data

ENTRYPOINT ["java","-jar","/carana-virus.jar"]


