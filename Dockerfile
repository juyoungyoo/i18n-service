FROM java:openjdk-8-jre

COPY build/libs/i18n-service-1.0.jar /usr/home/
RUN chmod +x /usr/home/i18n-service-1.0.jar
WORKDIR /usr/home

EXPOSE 8080
CMD ["java", "-Dspring.profiles.active=dev", "-jar", "i18n-service-1.0.jar"]