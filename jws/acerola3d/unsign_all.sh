#!/bin/sh

jars=`find . -name '*.jar'`

for j in $jars
do
    echo unjarsigner $j
    rm -fr tmp
    mkdir tmp
    cd tmp
    jar xf ../$j
    rm -fr META-INF
    rm ../$j
    jar cf ../$j *
    cd ..
done

rm -fr tmp
