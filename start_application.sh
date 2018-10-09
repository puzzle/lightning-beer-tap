#!/usr/bin/env bash

# Java call parameters
WEBAPP="wss://ln-self-order-pos-dev.ose3.puzzle.ch/websocket/invoice?access_token="
OPTS="-Xms128M -Xmx128M -jar"
JARFILE="websocket-bridge-0.0.1-SNAPSHOT.jar"
JARPATH="websocket_bridge/build/libs/"
TOPIC="/topic/invoice"
COMMAND="./gpio_handler/beerme.py"

# Enable dashboard
source dashboard/dashboard.sh

if [ ! -f $JARFILE ]; then
	cp "$JARPATH$JARFILE" .
fi

# Enable websocket bridge
nohup java $OPTS $JARFILE --url=$WEBAPP --topic=$TOPIC --command=$COMMAND &

