FROM openjdk:8-jre

ENTRYPOINT ["java", "-jar", "/usr/share/myservice/app.jar"]

VOLUME /usr/share/myservice

RUN mkdir -p /usr/share/myservice

WORKDIR /usr/share/myservice

ADD /target/web.jar /usr/share/myservice/app.jar