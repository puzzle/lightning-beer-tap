#!/usr/bin/env bash

# Java call parameters
WEBAPP="wss://ln-self-order-pos-dev.ose3.puzzle.ch/websocket/invoice?access_token="
OPTS="-Xms128M -Xmx128M -jar"
JARFILE="websocket-bridge-0.0.1-SNAPSHOT.jar"
JARPATH="websocket_bridge/build/libs/"
TOPIC="/topic/invoice"
COMMAND="./gpio_handler/gpio_handler.py"

usage(){
cat << EOF                                                                              
Usage: $0 [OPTIONS]                                          

This script handles all interactions with the lightning powered beertap.

OPTIONS:
	start	Initializes the device, starts the dashboard and web bridge
	stop	Stops all services
	build	Build or rebuild the java web bridge to the lightning node
                                                                                           
EXAMPLES:                                                                               
    application restart

EOF
}

# Start all services on the beer tap device
app_start(){
	# Check if the websocket bridge has been built
	if [ ! -f $JARPATH$JARFILE ]; then
		app_build
	fi
	# Start up the dashboard
	source dashboard/dashboard.sh
	# Hide mouse when still
	#DISPLAY=:0 unclutter -idle 0.01 -root &
	# Start websocket bridge, fork to background and no output
	nohup java $OPTS $JARPATH$JARFILE --url=$WEBAPP --topic=$TOPIC --command=$COMMAND & >/dev/null 2>&1
}

# Stop all services
app_stop(){
	echo "Killing all services..."
	killall java
	killall chromium-browser
	killall unclutter
}

# Build or rebuild the java lighning node web bridge
app_build(){
	echo "Building the websocket bridge please wait"
	cd websocket_bridge && exec ./gradlew build >/dev/null 2>&1
}


# Argument parsing
case $1 in
	start)
	app_start
	exit 0;
	;;

	stop)
	app_stop
	exit 0;
	;;

	build)
	app_build
	exit 0;
	;;

	restart)
	app_stop
    app_start
	exit 0;
	;;

	*)
	usage
	exit 0;
	;;
esac
