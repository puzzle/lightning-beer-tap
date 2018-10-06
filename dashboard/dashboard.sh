#!/usr/bin/env bash

# Arguments
SHOP="https://ln-self-order-pos-dev.ose3.puzzle.ch/#/self-service-landscape/puzzleUp"
KIOSK_ARGS="--kiosk --disable-translate --incognito --app=$SHOP"

# Don't sleep, don't blank, waste energy!
DISPLAY=:0 xset s off
DISPLAY=:0 xset s noblank
DISPLAY=:0 xset -dpms

# Start app in kiosk mode
DISPLAY=:0 nohup chromium-browser $KIOSK_ARGS &> /dev/null &
