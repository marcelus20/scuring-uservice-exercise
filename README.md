## Securing Microservices Exercise

This project is just an exercise of implementing JWT to secure microservices. 

IMPORTANT: Project is supposed to be run using the docker-compose, don't run each service separately from
jar or IDE play button, since the microservices need to be networked together to be able to communicate.
Instructions for running the docker-compose will be listed bellow.


## How to deploy

Firstly, clone the repo, if you haven't already:
```shell
git clone https://github.com/marcelus20/securing-uservice-exercise
```

Secondly, move to the cloned folder:
```shell
cd securing-userservice-exercise
```

Thirdly, run mvn clean install **from the parent directory** to generate the cluster-compose/target/classes/docker-compose.yaml
```shell
mvn clean install
```
PS: Don't run the docker compose from src folder as it will not work. You must run it from 
**cluster-composer/target/classes/docker-compose.yaml** after performing the mvn clean install.

Lastly, run the docker-compose 
```shell
docker-compose -f cluster-composer/target/classes/docker-compose.yaml up -d
``` 
## Structure
The project is composed of 3 microservices:
* gateway-service
* main-api-service
* security-service

### gateway-service
This is the proxy. It will receive the requests and foward them to the other microservices, so that the client
doesn't need to communicate directly to the other microservices.

### main-api-service
This is the service that is secured. For the scope of this exercise, there is only one endpoint available in it,
the /health endpoint. However, the only way of accessing this endpoint is by having obtained the JWT token
supplied by the security-service.

### security-service
This is the service that generates the JWT and validate requests. The available endpoints for this service is
/auth (creates JWT when correct username and password are provided), and /validate (validates the headers of the request
against the Bearer token).

#### Credentials

For the purpose of this exercise, the credentials are hardcoded and no encryption is being performed.
* username: "user"
* password: "password"


## Using the application

You will essentially interact with the gateway. After deployed, use an HTTP client of your preference (Postman, curl,
etc), and send a get request to localhost/heath. 
Because you haven't authenticated, you should get a 403. 


### Hitting the /health endpoint without the JWT
The gateway-service will forward to the request to the main-api-service, and, 
because it's secured, you should get a 403 forbidden.

#### Request
```shell
curl -v localhost:health
```

#### Response
```shell
*   Trying 127.0.0.1:80...
* Connected to localhost (127.0.0.1) port 80 (#0)
> GET /health HTTP/1.1
> Host: localhost
> User-Agent: curl/7.81.0
> Accept: */*
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 403 Forbidden
< Content-Length: 0
< Date: Sun, 11 Dec 2022 01:29:54 GMT
< 
* Connection #0 to host localhost left intact
```

### Authenticating
You should send a POST to localhost/auth. The gateway will forward the request to the 
security-service, and it will authenticate and generate the token. 

#### Request
```shell
curl -H "Content-Type: application/json" -d '{"username":"user", "password":"password"}' -X POST localhost/auth
```

#### Response
```json
{"jwt":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoiU2VjdXJpdHktU2VydmljZSIsImV4cCI6MTY3MDcyMzIyMywiaWF0IjoxNjcwNzIyNjIzfQ.uYSs1OIlXySoOLx_oRcQcwA3yrx1_Uqx6u6aieyPWPceLTYgDH-VOMT2F9nb3dxBtNAh_CTjSd-3SkzkySE9Gg"}
```

### Hitting health endpoint providing the JWT:
This time, you should get a "This api is healthy!" message because you provided the request with the token in the header.
The header key must be **Authorization** and the value must begin with **Bearer**, followed by an empty space and the token
(please, see the example below).

#### Request
```shell
curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoiU2VjdXJpdHktU2VydmljZSIsImV4cCI6MTY3MDcyMzIyMywiaWF0IjoxNjcwNzIyNjIzfQ.uYSs1OIlXySoOLx_oRcQcwA3yrx1_Uqx6u6aieyPWPceLTYgDH-VOMT2F9nb3dxBtNAh_CTjSd-3SkzkySE9Gg" localhost/health
```

#### Response
```json
{"message":"This api is healthy!","date":"2022-12-11T01:39:48.614+00:00"}
```

## License 
[GNU GENERAL PUBLIC LICENSE](LICENSE)