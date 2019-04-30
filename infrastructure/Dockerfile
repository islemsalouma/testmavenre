FROM tomcat:8.5-jre8

WORKDIR /usr/local/tomcat

RUN rm -rf /usr/local/tomcat/webapps/ROOT

ADD application.properties .

ADD crm-application.war ./webapps/ROOT.war

CMD ["catalina.sh", "run"]