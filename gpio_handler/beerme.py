#!/usr/bin/python
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
t_large_beer = 1
t_small_beer = 0.5

def cli_args_parser():
    """ argument parser """
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
    """ Setup all GPIOs """
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(r_ch1,GPIO.OUT)
    GPIO.setup(r_ch2,GPIO.OUT)
    GPIO.setup(r_ch3,GPIO.OUT)

def __set_gpio(channel=r_ch1, value=GPIO.LOW):
    """ Try to safely change the value of a gpio """
    try:
        GPIO.output(channel, value)
    except:
        print("GPIO Error")
        GPIO.cleanup()

def gpio_test():
    """ Test all channels """
    # Test channel 1 
    __set_gpio(r_ch1, GPIO.LOW)
    print("Channel 1:The Common Contact is access to the Normal Open Contact!")
    time.sleep(0.5)
    __set_gpio(r_ch1, GPIO.HIGH)
    print("Channel 1:The Common Contact is access to the Normal Closed Contact!\n")
    time.sleep(0.5)

    # Test channel 2
    __set_gpio(r_ch2, GPIO.LOW)
    print("Channel 2:The Common Contact is access to the Normal Open Contact!")
    time.sleep(0.5)
    __set_gpio(r_ch2, GPIO.HIGH)
    print("Channel 2:The Common Contact is access to the Normal Closed Contact!\n")
    time.sleep(0.5)

    # Test channel 3
    __set_gpio(r_ch3, GPIO.LOW)
    print("Channel 3:The Common Contact is access to the Normal Open Contact!")
    time.sleep(0.5)
    __set_gpio(r_ch3, GPIO.HIGH)
    print("Channel 3:The Common Contact is access to the Normal Closed Contact!\n")
    time.sleep(0.5)

def draw_large_beer(wait=t_large_beer):
    """ Draw a delicious large beer, keep the tap on for n_wait seconds """
    __set_gpio(r_ch1, GPIO.HIGH)
    time.sleep(wait)
    __set_gpio(r_ch1, GPIO.LOW)

def draw_small_beer(wait=t_small_beer):
    """ Draw a delicious small beer, keep the tap on for n_wait seconds """ 
    __set_gpio(r_ch1, GPIO.HIGH)
    time.sleep(wait)
    __set_gpio(r_ch1, GPIO.LOW)

# if not loaded as module
if __name__ == "__main__":
    # parse arguments
    args = cli_args_parser()
    
    # Setup all gpio pins
    __setup_GPIO()

    # call functions
    if args.test:
        print("Test mode enabled")
        gpio_test()
    elif args.product == "large":
        print("Choice: Large beer")
        draw_large_beer(t_large_beer)
    elif args.product == "small":
        print("Choice: Small beer")
        draw_small_beer(t_small_beer)
    else:
        print("RTFM!")
