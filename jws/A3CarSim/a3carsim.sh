#!/bin/sh

cp ~/workspace/A3CarSim/release/a3carsim.jar .
jarsigner -keystore ~/myKeystore a3carsim.jar mykey

