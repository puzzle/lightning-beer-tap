#!/bin/bash
curl --socks5-hostname $TOR_HOST:$TOR_PORT $WEBHOOK_URL
