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

# Hide mouse
DISPLAY=:0 unclutter -idle 0.01 -root &

# Enable websocket bridge
nohup java $OPTS $JARPATH$JARFILE --url=$WEBAPP --topic=$TOPIC --command=$COMMAND &

