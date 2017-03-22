# CrowdFireTestEnhancements
Goal of the Task:

Find the "Best time to post on Instagram" where you can get more eyeballs.


Your task is very simple. You need to make a connection to Instagram and pull all the followers list of a particular Instagram user. You will get Instagram userId or username as input. Pull their last posted time and based on that calculate the same of the day when most users are actively posting. Similarly which day of the week are they mostly posting.


This must be a web interface where there will be two input boxes. One input box will take Instagram user id as input and other input box will take the Instagram username as input. After taking user id or username as input, you need to print best time and also the best day to post for that user


Steps to Run:
=============
1. Place the war in Web Application Server/ or load it from Host Manager (using admin credentials)
2. Restart the service
3. CrowdFireAppInstagramService depends upon `CrowdFireAppInstagramApiClient` and `CrowdFireAppUtility`
4. CrowdFireAppWebApp is the client Application. Run the index file and you're done !

Incase, you face CORS issue, then hit the API directly
host:port/CrowdFireAppInstagramService/crowdfire/user/timetopost?username=username|userid

example::
localhost:8080/CrowdFireAppInstagramService/crowdfire/user/timetopost?username=asifsid88


Points to Remember:
===================
1. Place the war files in the tomcat webapp (tomcat container) [CrowdFireAppInstagramService.war] ==> This is the REST Service exposed to the client application
2. Place the CrowdFireAppWebApp folder in the same webapp (This is to ensure that webservice and application has the same domain, to prevent CORS issue). As currently with this assignment (CORS) issue is handled but not that properly (not checked/ covered all corner cases regarding CORS).
3. In real-time application, all the System.out.println() will be replaced with Log4j logging. And also have better logging system
4. Credentials, and some properties can be moved into `properties file`
5. Enter only `user-id` in the text field


Enhancements:
=============
Introduction of Polling System. Here the AJAX call will not hold itself for the request to get complete. Rather, it will poll on intervals to fetch the remaining responses (till we have the response remaining). At every request, the response will consists of `pollStatus:inprogress` or `pollStatus:complete`. Till we have complete status, the AJAX call will keep polling to get the fresh responses

The responses are stored in Poll system and intermediantly it is fetched. In the middle, if the status is `inprogress` and any other client is trying to hit with the same userid, then the response is fetch from the Poll System (which is already having some state).
If the status is `complete` and a request with same userid is made, then new response will be fetch using APIs.


