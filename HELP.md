#**Temperature Request Service**
###Purpose - returning temperature of city,and return all requests that was done during period of time. 
***
_Details - Using name of city that was passed as the parameter returning current temperature on this city if it is storing in API_1 database. 
Request date, city name and current temperature is saved in DynamoDB for 20 minutes.
Using lowBound and highBound of period of time (in linux epoch format) user can get all requests that was done during that period_

---
Most important classes :

**RequestLoggingFilter** - filter, suppose to log all requests

**MainController** - controller class that is head on taking cityName and bounds of period of requests.

**TemperatureRequest** - entity that contains city name, temperature and date of request, suppose to stored in DB.

**SwaggerConfig** - swagger file where all swagger configuration is and should be storing.
