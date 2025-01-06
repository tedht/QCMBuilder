@echo off

REM Classpath
set CLASSPATH=lib\*;bin;.

REM Compilation
javac -cp %CLASSPATH% @compile.list -d bin -encoding UTF-8

REM Exécution
java -cp %CLASSPATH% controleur.Controleur

pause