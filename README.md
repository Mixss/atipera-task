# github repositories REST-API

This REST API provides one endpoint which returns the list of repositories that are not forks for given owner. 

## Table of contents

- [Compiling and running](#compiling-and-running)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Error handling](#error-handling)

## Compiling and running
- Using `maven package` and `java -jar`:
```
$ ./mvnw package
$ java -jar target/github_api-1.0.jar
```
- Executing directly with maven
```
$ ./mvnw compile exec:java
```
- Using docker (you can specify your own port):
```
$ ./mvnw spring-boot:build-image
$ docker run -p 8080:8080 docker.io/library/dynatrace-backend-task:1.0
```

## Usage

Application by default will be available at port `8080`.

## Endpoints
- **GET** `/repos/{username}`:
    - takes arguments:
        - `username` - the name of github user
    - returns:
      - list of repositories for given user with its branches and last commit shas:
        ```json
            {
              "owner": "Mixss",
              "repositories": [
                  {
                      "name": "atipera-task",
                      "branches": [
                          {
                              "name": "main",
                              "lastCommitSha": "fd2915099ba7516f89fb529338ec10f941845736"
                          }
                      ]
                  },
                  {
                      "name": "statystyk-codzienny",
                      "branches": [
                          {
                              "name": "holidays",
                              "lastCommitSha": "debba4b4a6e2e3c25a92d4f492b118023b22819a"
                          },
                          {
                              "name": "master",
                              "lastCommitSha": "ab8010e673219b8d4ac4bbdbf17d19c39251b3b5"
                          },
                          {
                              "name": "new",
                              "lastCommitSha": "b75f21e52f43d507480f9ca9b56afdd84fd78289"
                          }]
                  }]
            }
        ```
      
## Error handling
If an error occurs, a JSON containing the HTTP status code and message is returned:
```json
{
  "status": 404,
  "Message": "Data not found"
}
```
