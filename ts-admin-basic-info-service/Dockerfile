FROM openjdk:8

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

COPY target/ts-admin-basic-info-service-1.0.jar /app/ts-admin-basic-info-service-1.0.jar
ENTRYPOINT ["java", "-Xmx200m",  "-jar", "/app/ts-admin-basic-info-service-1.0.jar"]

EXPOSE 18767