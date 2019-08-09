#!/usr/bin/env bash

DIR="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)"
source ${DIR}/configuration.sh

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
	JAR=${BRIDGE_JARPATH}${BRIDGE_JARFILE}
	# Check if the websocket bridge has been built
	if [ ! -f ${JAR} ]; then
		app_build
	fi
	# Start up the dashboard
	source ${DIR}/dashboard/dashboard.sh
	# Hide mouse when still
	#DISPLAY=:0 unclutter -idle 0.01 -root &
	# Start websocket bridge, fork to background and no output
	nohup java ${BRIDGE_JAVA_OPTS} ${JAR} --url=${WEBSOCKET_URL} --topic=${WEBSOCKET_TOPIC} --command=${GPIO_HANDLER_COMMAND} --prefix=${MEMO_PREFIX} & >/dev/null 2>&1
}

# Stop all services
app_stop(){
	echo "Killing all services..."
	killall java
	pkill -o chromium
	killall unclutter
}

# Build or rebuild the java lighning node web bridge
app_build(){
	echo "Building the websocket bridge please wait"
	cd ${DIR}/websocket_bridge && exec ./gradlew build >/dev/null 2>&1
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
