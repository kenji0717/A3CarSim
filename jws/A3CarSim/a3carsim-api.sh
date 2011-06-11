#!/bin/sh

cp ~/workspace/A3CarSim/release/a3carsim-api.jar .
jarsigner -keystore ~/myKeystore a3carsim-api.jar mykey

