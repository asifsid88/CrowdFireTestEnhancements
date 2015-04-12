ECHO OFF

ECHO Building and installing CrowdFireAppUtility
cd CrowdFireAppUtility
call mvn clean install

ECHO Building and installing CrowdFireAppInstagramApiClient
cd ../CrowdFireAppInstagramApiClient
call mvn clean install
PAUSE