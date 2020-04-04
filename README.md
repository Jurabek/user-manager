
# Getting started
User management tool developed using Service Layer architecture including IoC and other Design Patterns.
 
## Prerequisites

- Running in docker
  - `Docker Desktop (Mac, Linux, Windows)`
- Running locally
  - `jdk-1.8`
  - `gradle`
  
 
To get up it running use `docker-compose up` or `./gradlew bootRun`
 
Examples

- Create new user
```shell script
curl --request POST \
  --url http://localhost:8090/api/v1/users \
  --header 'content-type: application/json' \
  --data '{
    "id": "0aa2b7e5-4a61-4832-be89-6e1362a4e9fa",
    "name": "Test user",
    "lastName": "Test user",
	"position" : "Tester",
    "githubUrl": "https://github.com/testuser"
}'
```
- Update existing user
```shell script
curl --request PUT \
  --url http://localhost:8090/api/v1/users/0aa2b7e5-4a61-4832-be89-6e1362a4e9fa \
  --header 'content-type: application/json' \
  --data '{
  "id": "0aa2b7e5-4a61-4832-be89-6e1362a4e9fa",
  "name": "Test user 1",
  "lastName": "Test user 1",
  "position": "Tester",
  "githubUrl": "https://github.com/testuser"
}'
```
- User info by Id
```shell script
curl --request GET \
  --url http://localhost:8090/api/v1/users/0aa2b7e5-4a61-4832-be89-6e1362a4e9fa
```
- Get user repositories
```shell script
curl --request GET \
  --url http://localhost:8090/api/v1/users/0aa2b7e5-4a61-4832-be89-6e1362a4e9fa/repos
```
- Get all users
```shell script
curl --request GET \
  --url http://localhost:8090/api/v1/users
``` 

To running tests use:
```bash
sh test.sh & sh qa.sh 
```

Quality Assurance  
- `Sonar Qube` reports can be found here: [SonarCloud](https://sonarcloud.io/dashboard?id=sample-user-management)
- Coverage results can be found here: `./build/reports/jacoco/test/html/index.html`