#!/bin/bash

# Compilation
javac -cp %CLASSPATH% @compile.list -d bin -encoding UTF-8

# Execution
cd bin
java -cp %CLASSPATH% controleur.Controleur

read -p "Press any key to continue..."