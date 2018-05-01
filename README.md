# Coordinator [![Build Status](https://travis-ci.org/gmcoringa/coordinator.svg)](https://travis-ci.org/gmcoringa/coordinator)

This project provides Zookeeper node tree navigation, content visualization and content modification. Also displays Zookeeper cluster state.

## Running

**Requires** Zookeeper running.

Run can be simple as that:

```shell
# By default enables debug on port 5005 and enables development profile
./gradlew bootRun
``` 

Running the generated jar:

```shell
./gradlew build
SPRING_PROFILES_ACTIVE="development" java -jar build/libs/coordinator-$VERSION-assembly.jar
```

## Configuration

As this project uses Spring Boot the entire application.yml que be replaced by an external file.

- **zookeeper.hosts**: Zookeeper connection string, format ``host:port``, required. For production profile can be set with ``ZK_QUORUM`` environment variable.
- **zookeeper.connection.retries** : Number of connections retries before giving up, default `3`.
- **zookeeper.connection.timeoutMS**: Connection timeout in miliseconds, default `5000`.
- **zookeeper.session.timeoutMS**: Session timeout in miliseconds, default `60000`.
- **zookeeper.cache.instancesTimeoutMS**: Cluster state cache timeout in miliseconds, default `60000`.
- **auth.enabled**: enables authentication, default `false`.
- **auth.user.name**: user name for authentication.
- **auth.user.password**: user password for authentication.

The Zookeeper connection statregy ``ExponentialBackoffRetry`` with a base sleep time of 1000 miliseconds.
  
## Authentication

When authentication is enabled, all resources requires an logged user for access. The basic authentication is used.
