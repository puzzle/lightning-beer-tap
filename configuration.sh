#!/usr/bin/env bash

WEBSOCKET_URL="wss://localhost/websocket/invoice?access_token="
FRONTEND_URL="https://localhost/#/self-service-landscape/[pos-Identification]"

BRIDGE_JAVA_OPTS="-Xms128M -Xmx128M -jar"
BRIDGE_JARFILE="websocket-bridge-0.0.1-SNAPSHOT.jar"
BRIDGE_JARPATH="websocket_bridge/build/libs/"
WEBSOCKET_TOPIC="/topic/invoice"
GPIO_HANDLER_COMMAND="./gpio_handler/gpio_handler.py"

