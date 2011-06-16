#!/bin/sh

CLASSPATH='/Users/ksaito/JARS/acerola3d/*:/Users/ksaito/workspace/A3CarSim/lib/ecj-3.6.2.jar:'
export CLASSPATH

javadoc -d api -charset UTF-8 -docencoding UTF-8 -encoding UTF-8 /Users/ksaito/workspace/A3CarSim/src/com/github/kenji0717/a3cs/*.java

