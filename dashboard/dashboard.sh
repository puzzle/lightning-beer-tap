#!/usr/bin/env bash

# Arguments
SHOP="https://localhost/#/self-service-landscape/[pos-Identification]"
KIOSK_ARGS="--kiosk --disable-translate --incognito --app=$SHOP"

# Don't sleep, don't blank, waste energy!
DISPLAY=:0 xset s off
DISPLAY=:0 xset s noblank
DISPLAY=:0 xset -dpms

# Start app in kiosk mode
DISPLAY=:0 nohup chromium-browser $KIOSK_ARGS &> /dev/null &
