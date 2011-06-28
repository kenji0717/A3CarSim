#!/bin/sh

echo "Enter Path of the keystore."
echo "if you use default keystore, press enter."
echo "path: \c"
read keystore
if [ "$keystore" = "" ]
then
    keystore=$HOME/.keystore
fi

echo ""
echo "Enter key alias for sign."
echo "key: \c"
read keyalias

trap 'stty echo' INT
stty -echo

echo ""
echo "Enter the Password of the keystore."
echo "password: \c"
read storepass

echo ""
echo ""
echo "Enter the Password of the key."
echo "password: \c"
read keypass

stty echo
trap '' INT

echo ""
echo ""
#echo "keystore :" $keystore
#echo "keyalias :" $keyalias
#echo "storepass:" $storepass
#echo "keypass  :" $keypass

jars=`find . -name '*.jar'`

for j in $jars
do
    echo jarsigner $j
    jarsigner -keystore $keystore -storepass $storepass -keypass $keypass $j $keyalias
done

