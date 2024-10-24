#!/bin/bash
echo "Компиляция "
javac  -cp src src/EcosystemSimulator.java -d out
echo "Запуск "
java -cp out EcosystemSimulator