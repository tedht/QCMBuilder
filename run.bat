@echo off

REM Compilation
javac @compile.list -d bin 

REM Exécution
cd bin
java Controleur

pause