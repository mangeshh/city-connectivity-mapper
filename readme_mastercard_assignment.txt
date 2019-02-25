Read me :

application.properties file having path for the file, has city pairs.
server port is set to 8080.

Sample URL:
http://localhost:8080/connected?origin=Boston&destination=Newark

To see rest APIS:
http://localhost:8080/swagger-ui.html --> list operations.

Note:
There are 2 different API provided, 1st one is using adjustant matrix this is be slower as number of cities increased.
so implemented 2nd API "/connected", this will give better performance in stress situation also.
2nd API is ~3 times better in response time, for now all requests are handled by this 2nd API only.
To hit the 2nd API we run - http://localhost:8080/connected?origin=Boston&destination=Newark
request mapping for 2nd API is "/connected".


github - checkout

GIT URL - https://github.com/mangeshh/city-connectivity-mapper/tree/master/complete
import as maven project

SWAGGER - http://localhost:8080/swagger-ui.html

NOTE : input file with city pairs (New York, Newak...) is specified inside application.properties ans key is cache.input.file