FROM tomcat:10.1-jdk17-temurin AS build

WORKDIR /app

COPY src/main/java /app/src/main/java

RUN mkdir -p /app/classes && \
    javac -cp /usr/local/tomcat/lib/servlet-api.jar \
    -d /app/classes \
    $(find /app/src/main/java -name "*.java")


FROM tomcat:10.1-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY src/main/webapp /usr/local/tomcat/webapps/ChatApp
COPY --from=build /app/classes/mypackage /usr/local/tomcat/webapps/ChatApp/WEB-INF/classes/mypackage
COPY --from=build /app/classes/url_manager /usr/local/tomcat/webapps/ChatApp/WEB-INF/classes/url_manager

CMD sed -i "s/port=\"8080\"/port=\"${PORT:-10000}\"/" /usr/local/tomcat/conf/server.xml && catalina.sh run
