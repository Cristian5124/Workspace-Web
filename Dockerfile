FROM openjdk:21-jdk-slim

WORKDIR /usrapp/bin

ENV PORT=8080
EXPOSE 8080

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency

CMD ["java","-cp","./classes:./dependency/*","edu.escuelaing.app.Application"]