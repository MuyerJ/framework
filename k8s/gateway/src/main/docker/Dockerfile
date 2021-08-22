FROM openjdk:8-jre-alpine

ADD gateway-1.0.0.RELEASE.jar /usr/share/app.jar

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS  -jar /usr/share/app.jar
