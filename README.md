# driver-pete-token-fetcher
Fetches authorization token from the driver pete server.

## Building

```./gradlew fatJar```

Artefact driver-pete-token-fetcher.jar will be in build/libs


## Usage

```
usage: driver-pete-token-fetcher
 -address <arg>   Server address (e.g. https://localhost:8443)
 -email <arg>     Facebook email (e.g. testuser@gmail.com)
 -pass <arg>      Facebook password
 ```

###Example:

```java -jar driver-pete-token-fetcher.jar -address https://localhost:8443 -email <FACEBOOK_EMAIL> -pass <FACEBOOK_PASSWORD>```
