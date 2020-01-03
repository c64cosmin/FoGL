#!/bin/sh

rm -rf output
rm -rf target
rm fogl.7z

mvn package
mkdir output
cp target/fogl.jar output/.
mkdir output/res
cp res/* output/res/.

pushd output
echo "java -jar FoGL.jar" > FoGL.bat
echo "java -jar FoGL.jar SoGL %1" > SoGL.bat

PWDESC=`echo $PWD | sed 's_/_\\\\_g' | sed 's_\\\\c_C:_' `
echo "cd $PWDESC\\" > SoGL_exec.bat
echo "SoGL.bat %1" >> SoGL_exec.bat
popd

mkdir output/convertors
gcc convertors/fogl_convertor.c -o output/convertors/fogl_convertor.exe -std=gnu11
gcc convertors/sogl_convertor.c -o output/convertors/sogl_convertor.exe -std=gnu11

7z a fogl.7z ./output/*
