FROM openjdk:11
EXPOSE 8080
ADD target/carana-virus.jar carana-virus.jar
ENTRYPOINT ["java","-jar","/carana-virus.jar"]