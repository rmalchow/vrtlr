FROM rmalchow/springboot
MAINTAINER Ruben Malchow <ruben.malchow@mcon-group.com>
COPY shrtnr-app/target/shrtnr-app.jar shrtnr-app.jar
COPY shrtnr-app/config/application.yml ./
CMD [ "-j" , "shrtnr-app.jar", "-o", "-Xmx512m" ]