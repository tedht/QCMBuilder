#!/bin/bash

# Compilation
javac @compile.list -d bin

# Execution
cd bin
java Controleur

read -p "Press any key to continue..."