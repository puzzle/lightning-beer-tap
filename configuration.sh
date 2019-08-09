#!/usr/bin/env bash

DIR="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)"

# ----------------------------------------------
# you probably need to edit the following values
# ----------------------------------------------

# the URL where the websocket bridge will connect to to listen for invoices
WEBSOCKET_URL="wss://localhost/websocket/invoice?access_token="

# the URL that is opened in the browser to show the GUI on the screen
FRONTEND_URL="https://localhost/#/self-service-landscape/[pos-Identification]"

# only listen to paid invoices that have the following text at the beginning of their memo
MEMO_PREFIX="beerTap"

# ----------------------------------------------
# most likely you don't need to change anything here
# ----------------------------------------------
CHROME_ZOOM="1.0"
BRIDGE_JAVA_OPTS="-Xms128M -Xmx128M -jar"
BRIDGE_JARFILE="websocket-bridge-0.0.1-SNAPSHOT.jar"
BRIDGE_JARPATH="websocket_bridge/build/libs/"
WEBSOCKET_TOPIC="/topic/invoice"
GPIO_HANDLER_COMMAND="${DIR}/gpio_handler/gpio_handler.py"

