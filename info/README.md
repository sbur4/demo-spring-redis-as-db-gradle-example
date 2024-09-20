1. redis db
post http://localhost:8080/api/fname/lanme

get http://localhost:8080/api/id
get http://localhost:8080/api/user/super

put http://localhost:8080/api/user/id&newname

delete http://localhost:8080/api/user/id

2. docker => 
- redis/redis-stack:latest
port:6379
port:8001
- mariadb:latest
port:3306