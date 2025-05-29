#!/bin/bash

# Limpa a pasta de saída e a recria
rm -rf output
mkdir -p output

# Compila todos os arquivos .java, preservando a estrutura de pacotes
find . -name "*.java" -not -path "./output/*" | xargs javac -d output

# Executa a classe principal
java -cp output Main