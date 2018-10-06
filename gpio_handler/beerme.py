#!/usr/bin/env python
# -*- coding:utf-8 -*-

#######################
# P26 ----> r_ch1 #
# P20 ----> r_ch2 #
# P21 ----> r_ch3 #
#######################

import RPi.GPIO as GPIO
import argparse
import time

# Channels
r_ch1 = 26
r_ch2 = 20
r_ch3 = 21

# time constants in seconds
t_large_beer = 5
t_small_beer = 2

# Syntax suger because of negative logic
S_ON = GPIO.LOW
S_OFF = GPIO.HIGH

def cli_args_parser():
    """ 
        Argument parser configuration 
    """
    parser = argparse.ArgumentParser(
            description='Lightning beer tap cli',
            formatter_class=argparse.ArgumentDefaultsHelpFormatter
    )

    parser.add_argument(
            '-p',
            '--product',
            action='store',
            dest='product',
            help="Product description, beer size [small, large]"
    )

    parser.add_argument(
            '-t',
            '--test',
            action='store_true',
            help="Test mode which tests all available channels"
    )

    parser.add_argument(
            '-m',
            '--memo',
            action='store',
            dest='memo',
            help="Precise product information"
    )

    return parser.parse_args()

def __setup_GPIO():
    """ 
        Setup all GPIOs, set output mode, and set gpio mode to bcm
    """
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)

    for gpio in [r_ch1, r_ch2, r_ch3]:
        print("setting up gpio_{}".format(gpio))
        GPIO.setup(gpio, GPIO.OUT)
        __set_gpio(gpio, S_OFF)

def __set_gpio(channel=r_ch1, value=S_OFF):
    """ 
        Try to safely change the value of a gpio, catch exception if it fails
        TODO: Exception Handling
    """
    try:
        GPIO.output(channel, value)
    except:
        print("GPIO Error")
        GPIO.cleanup()

def gpio_test():
    """ 
        Test all channels
    """
    for i, gpio in enumerate([r_ch1, r_ch2, r_ch3], start=1):
        __set_gpio(gpio, S_ON)
        print("Channel_{}: gpio_{} on".format(i, gpio))
        time.sleep(0.1)
        __set_gpio(gpio, S_OFF)
        print("Channel_{}: gpio_{} off".format(i, gpio))
        time.sleep(0.1)

def draw_beer(wait=t_large_beer):
    """ 
        Draw a delicious beer, keep the tap on for n_wait seconds 
    """
    __set_gpio(r_ch1, S_ON)
    time.sleep(wait)
    __set_gpio(r_ch1, S_OFF)

if __name__ == "__main__":
    """
        Main if not loaded as module
    """
    # parse arguments
    args = cli_args_parser()
    
    # Setup all gpio pins
    __setup_GPIO()

    # call functions according to the given arguments
    if args.test:
        print("Test mode enabled")
        gpio_test()
    elif args.product == "large":
        print("Choice: Large beer")
        draw_beer(t_large_beer)
    elif args.product == "small":
        print("Choice: Small beer")
        draw_beer(t_small_beer)
    else:
        print("RTFM!")
