# Betting application example
Stack: spring boot, angularjs, sockjs, mongodb, maven, spock, cucumber 

## Install
```
bower install
```

Then you can run Maven to package the application:
```
mvn clean package
```

## Test
```
mavn test
```
Functional tests:
```
mavn package
java -jar ./target/bet-1.0-SNAPSHOT.jar
```
Open new terminal window
```
cd test/functional
mavn clean test
```
