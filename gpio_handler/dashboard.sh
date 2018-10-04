#!/bin/bash

SHOP="https://ln-self-order-pos-dev.ose3.puzzle.ch/#/self-service/puzzleUp"

DISPLAY=:0 xset s off
DISPLAY=:0 xset s noblank
DISPLAY=:0 xset -dpms

DISPLAY=:0 nohup chromium-browser --kiosk --disable-translate --incognito --app="$SHOP" &> /dev/null &
