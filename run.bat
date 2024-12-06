@echo off

REM Compilation
javac @compile.list -d bin 

REM Exécution
cd bin
java controleur/Controleur

pause