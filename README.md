# Запуск приложения

* Через командную строку.

```shell
javac  -cp src src/EcosystemSimulator.java -d out
java -cp out EcosystemSimulator
```

* или выполните скрипт run.sh или run.bat или run.bash

1. Linux:

 ```sh
 chmod +x run.sh
 ./run.sh
 ```

2. Windows:

 ```sh
 run.bat
 ```

3. или через docker

```shell
docker build -t es .
docker run -it es
```

## Использование

- Запустите приложение и следуйте инструкциям в консоли.
- Сохранение итогов симуляция происходит только после корректного выхода.
- Сохранение логов после каждого шага

### Логика

Животные едят растения и пьют воду.
Если поели и попили и есть пара и температуру позволяет, то популяция животных растёт.
Растения потребляют влажность и если позволяет температура то растут.

## Выполнил

Егор


