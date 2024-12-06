#!/bin/bash

# Compilation
javac @compile.list -d bin

# Execution
cd bin
java controleur/Controleur

read -p "Press any key to continue..."