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
gcc convertors/fogl_convertor.c -o output/convertors/fogl_convertor.exe
gcc convertors/sogl_convertor.c -o output/convertors/sogl_convertor.exe

7z a fogl.7z ./output/*
