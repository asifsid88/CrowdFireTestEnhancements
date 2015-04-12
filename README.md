# CrowdFireTestEnhancements
Enhancement to the assigment --> Polling


Enhancement to https://github.com/asifsid88/CrowdFireTest repo.

Introduction of Polling System. where the AJAX call won't hold itself for the request. It will poll on intervals to fetch the remaining responses (till we have the response remaining). At every request, the response will consists of `pollStatus:inprogress` or `pollStatus:complete`. Till we have complete status, the AJAX call will keep polling to get the fresh responses

In the middle, if the status is `inprogress` and any other client is trying to hit with the same userid, then the response is fetch from the Poll System (which is already having some state).
If the statis is `complete` and a request with same userid is made, then new response will be fetch using APIs
