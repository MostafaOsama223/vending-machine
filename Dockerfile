FROM openjdk:17-jdk

LABEL "org.opencontainers.image.title"="Vending Machine Application"
LABEL "org.opencontainers.image.authors"="Mostafa O."

COPY build/libs/vending-machine-0.0.1-SNAPSHOT.jar vending-machine.jar

ENTRYPOINT ["java", "-jar", "vending-machine.jar"]