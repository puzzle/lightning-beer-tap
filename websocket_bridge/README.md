# WebSocket Bridge

The WebSocket Bridge connects to the invoices WebSocket provided by a running instance of the https://github.com/puzzle/ln-self-order-pos

## build java application
```bash
./gradlew build
```

## how to run

get the built application from ./build/libs

```bash
java -jar websocket-bridge-0.0.1-SNAPSHOT.jar --url=ws://localhost:8080/websocket/invoice?access_token= --topic=/topic/invoice --command=./dummy_command.sh
```

Options:

* url: the websocket url to connect to where the self order point application is running
* topic: the topic to subscribe to
* command: the shell command that is executed when a message was received on the websocket

Two Options will be routed to the shell command that is executed

* --memo
* --products

for example:
```
./dummy_command.sh --memo="puzzleUp #9c4a Grey Card (CHF 5.00)" --products=PRODUCT_1
```