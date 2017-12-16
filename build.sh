#!/bin/sh

rm -rf output
rm -rf target
rm fogl.7z

mvn package
mkdir output
cp target/fogl.jar output/.
mkdir output/res
cp res/* output/res/.

mkdir output/convertors
gcc convertors/fogl_convertor.c -o output/convertors/fogl_convertor.exe -std=gnu11
gcc convertors/sogl_convertor.c -o output/convertors/sogl_convertor.exe -std=gnu11

7z a fogl.7z ./output/*
