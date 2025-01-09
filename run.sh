#!/bin/bash

set CLASSPATH=lib\*;bin;.

# Compilation
javac -cp %CLASSPATH% @compile.list -d bin -encoding UTF-8

# Execution
java -cp %CLASSPATH% controleur.Controleur