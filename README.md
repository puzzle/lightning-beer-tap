# Lightning powered beer tap

This project consists of multiple parts. To make things easier the
`application.sh` provides a frontend to control everything. The different parts
of the project are documented below.

## Parts list

To build your own lightning powered beer tap you'll need the follwing parts. We
got most of our parts from distibutors located in switzerland, but you can get them elsewhere as well.

| Left Aligned | Centered | Right Aligned | Left Aligned | Centered | Right Aligned |
| :----------- | :------: | ------------: | :----------- | :------: | ------------: |
| Cell 1       | Cell 2   | Cell 3        | Cell 4       | Cell 5   | Cell 6        |
| Cell 7       | Cell 8   | Cell 9        | Cell 10      | Cell 11  | Cell 12       |

| **Partname**                   | **Partnumber** | **Price / CHF**  |  **Distributor**         |
| ------------------------------ | -------------- | ---------------- | ------------------------ |
| Raspberry Pi 3 B|              |  10760         |  39              |  pi-shop.ch              |
| 32GB MicroSD Card              |  6613018       |  29.9            |  digitec.ch              |
| Original Rpi 7" Touchscreen    |  10266         |  79.9            |  pi-shop.ch              |
| PiggiPi 7’’ Display Mount      |  10399         |  48.9            |  pi-shop.ch              |
| Raspberry Pi Relay Board       |  10398         |  24.9            |  pi-shop.ch              |
| 24V AC Source (24W/50Hz)       |  169-84-752    |  22.7            |  distrelec.ch            |
| Beer tap with solenoid valve   |  08.00125      |  247.3           |  schankanlagenhandel.eu  |
| Bent faucet for beer tap       |  08.00128      |  22.51           |  schankanlagenhandel.eu  |

## Installation

This is straightforward. If you bought an empty micro sd card, just download
the latest [Raspbian image](https://www.raspberrypi.org/downloads/raspbian/).
* Fire up `dd` to load the image to your card.
* Use `sudo raspi-config` to extend your partitions, start openssh and enable
the gpios
* Follow the guide on [how to secure you raspberry pi](https://www.raspberrypi.org/documentation/configuration/security.md)
* Once you've deployed your ssh key and secured your acces, clone this repo.
* Install the necessary software to your pi by executing the following
commands:
```bash
sudo apt-get update
sudo apt-get install -y  openjdk-8-jre openjdk-8-jdk unclutter vim
```
* Add your user to the group `gpio`
* You're all set and ready to go

# Aplication.sh start script

This script is used to start, stop or rebuild the application. Simple as that.
The websocket bridge will be automatically builded if you run `start` without a
previous build.

```bash
$ ./application.sh start # starts the dashboard and websocket bridge
$ ./application.sh stop  # stops everything
$ ./application.sh build # rebuilds the java websocket bridge
```

# WebSocket Bridge

## build application
```
./gradlew build
```

## how to run

get the built application from ./build/libs

```
java -jar websocket-bridge-0.0.1-SNAPSHOT.jar --url=wss://ln-self-order-pos-dev.ose3.puzzle.ch/websocket/invoice?access_token= --topic=/topic/invoice --command=./dummy_command.sh
```
Options:

* url: the websocket to connect to
* topic: the topic to subscribe to
* command: the shell command that is executed when a message was received on the websocket

Two Options will be routed to the shell command that is executed

* --memo
* --products

for example:
```
./dummy_command.sh --memo="puzzleUp #9c4a Grey Card (CHF 5.00)" --products=PRODUCT_1
```

# GPIO handler

To execute this python module the user has to be part of the group `gpio`.

## Dependencies

* RPi-GPIO (preinstalled on all raspberry pi distros)
* argparse

## how to run

```
python beerme.py --product=[large,small] [--test, --memo="asdf"]
```
