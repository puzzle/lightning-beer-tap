#!/bin/bash

SHOP="https://ln-self-order-pos-dev.ose3.puzzle.ch/#/self-service-landscape/puzzleUp"
KIOSK_ARGS="--kiosk --disable-translate --incognito --app=$SHOP"

DISPLAY=:0 xset s off
DISPLAY=:0 xset s noblank
DISPLAY=:0 xset -dpms

DISPLAY=:0 nohup chromium-browser $KIOSK_ARGS &> /dev/null &
