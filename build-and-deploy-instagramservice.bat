ECHO OFF

ECHO Building and installing CrowdFireAppUtility
cd CrowdFireAppUtility
call mvn clean install

ECHO Building and installing CrowdFireAppInstagramApiClient
cd ../CrowdFireAppInstagramApiClient
call mvn clean install

ECHO Building and packaging CrowdFireAppInstagramService
cd ../CrowdFireAppInstagramService
call mvn clean package

ECHO Deploying CrowdFireAppInstagramService.war on Tomcat
C:\cygwin\bin\curl --upload-file /cygdrive/c/projects/CrowdFireTest/CrowdFireAppInstagramService/target/CrowdFireAppInstagramService.war "http://admin:admin@localhost:8080/manager/text/deploy?path=/CrowdFireAppInstagramService&update=true"
PAUSE
