# Coordinator

This project provides Zookeeper node tree navigation, content visualization and content modification. Also displays Zookeeper cluster state.

## Running

**Requires** Zookeeper running.

Run can be simple as that:

```shell
# By default enables debug on port 5005 and enables development profile
gradle run
``` 

Running the generated jar:

```shell
gradle build
SPRING_PROFILES_ACTIVE="development" java -jar build/libs/coordinator-$VERSION.jar
```

## Configuration

As this project uses Spring Boot the entire application.yml que be replaced by an external file.

- **zookeeper.hosts**: Zookeeper connection string, format ``host:port``, required. For production profile can be set with ``ZK_QUORUM`` environment variable.
- **zookeeper.connection.retries** : Number of connections retries before giving up, default 3.
- **zookeeper.connection.timeoutMS**: Connection timeout in miliseconds, default 5000.
- **zookeeper.session.timeoutMS**: Session timeout in miliseconds, default 60000.

The Zookeeper connection statregy ``ExponentialBackoffRetry`` with a base sleep time of 1000 miliseconds.
