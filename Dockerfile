FROM maven:3.6.3-jdk-11
WORKDIR /app
COPY . .
#TODO Нужно подготовить базовый образ чтобы не скачивать зависимости каждый раз при сборке image
RUN mkdir -p /root/.m2 && \
    cp /app/settings.xml /root/.m2/

WORKDIR /app/demo-project-aft/common
RUN mvn install -DskipTests

WORKDIR /app/integration-tests
ENTRYPOINT ["/bin/sh", "start_test.sh"]