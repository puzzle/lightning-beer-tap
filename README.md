#WebSocket Bridge for our LND enabled Beer Tap

## build application
```
./gradlew build
```


##how to run

get the built application from ./build/libs

```
java -jar websocket-bridge-0.0.1-SNAPSHOT.jar --url=wss://ln-self-order-pos-dev.ose3.puzzle.ch/websocket/invoice?access_token= --topic=/topic/invoice --command=./dummy_command.sh
```
Options:

* url: the websocket to connect to
* topic: the topic to subscribe to
* command: the shell command that is executed when a message was received on the websocket

Two Options will be routed to the shell command that is executed

* --memo
* --products

for example:
```
./dummy_command.sh --memo="puzzleUp #9c4a Grey Card (CHF 5.00)" --products=PRODUCT_1
```