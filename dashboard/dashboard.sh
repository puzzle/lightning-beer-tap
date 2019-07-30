#!/usr/bin/env bash

DIR="$(cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd)"
source $DIR/../configuration.sh

# Arguments
KIOSK_ARGS="--kiosk --disable-translate --incognito --app=${FRONTEND_URL}"

# Don't sleep, don't blank, waste energy!
DISPLAY=:0 xset s off
DISPLAY=:0 xset s noblank
DISPLAY=:0 xset -dpms

# Start app in kiosk mode
DISPLAY=:0 nohup chromium-browser $KIOSK_ARGS &> /dev/null &
