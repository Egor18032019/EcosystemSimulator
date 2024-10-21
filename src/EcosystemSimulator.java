import logging.Logger;
import logging.LoggerCommon;
import models.Animal;
import models.NaturalEnvironment;
import models.Plant;
import services.EcosystemManager;
import services.ManagerCommon;
import utils.Const;

import java.io.File;
import java.util.Scanner;

public class EcosystemSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя файла для симуляции:");
        String filename = scanner.nextLine();
        LoggerCommon logger = Logger.getLogger();
        logger.setFileName(filename);
        ManagerCommon manager = new EcosystemManager(filename);
        NaturalEnvironment naturalEnvironment = manager.getNaturalEnvironment();
        while (true) {
            System.out.println("Ведите команду = номеру действия:");

            System.out.println("1. Добавить растение.");
            System.out.println("2. Добавить животное.");
            System.out.println("3. Показать данные симуляции.");
            System.out.println("4. Ввести температуру окружающей среды.");
            System.out.println("5. Ввести влажность окружающей среды.");
            System.out.println("6. Ввести кол-во доступной воды.");
            System.out.println("7. Запустить симуляцию.");
            System.out.println("0. Выход.");
            System.out.println("-1. Очистить данные.");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Введите имя растения:");
                    String plantName = scanner.nextLine();
                    System.out.println("Введите скорость роста в виде числа:");
                    int growthRate = scanner.nextInt();
                    System.out.println("Введите начальный вес растения:");
                    int weight = scanner.nextInt();
                    manager.update(new Plant(plantName, growthRate, weight));
                    break;
                case 2:
                    System.out.println("Введите имя животного:");
                    String animalName = scanner.nextLine();
                    System.out.println("Введите начальную популяцию:");
                    int population = scanner.nextInt();
                    System.out.println("Введите сколько ест за каждый тик:");
                    int eating = scanner.nextInt();
                    System.out.println("Введите сколько размножается за каждый тик:");
                    int reproduction = scanner.nextInt();
                    manager.update(new Animal(animalName, population, eating, reproduction));
                    break;
                case 3:
                    manager.printObjects();
                    break;
                case 4:
                    System.out.println("Введите температуру окружающей среды:");
                    naturalEnvironment.setTemperature(Integer.parseInt(scanner.nextLine()));
                    break;
                case 5:
                    System.out.println("Введите влажность окружающей среды:");
                    naturalEnvironment.setHumidity(Integer.parseInt(scanner.nextLine()));
                    break;
                case 6:
                    System.out.println("Введите кол-во доступной воды:");
                    naturalEnvironment.setWaterAvailable(Integer.parseInt(scanner.nextLine()));
                    break;
                case 0:
                    manager.save();
                    return;
                case 7:
                    runSimulation(manager, naturalEnvironment, logger);
                    break;
                case -1:
                    cleanDataDirectory(logger);
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
                    break;
            }


        }
    }

    private static void runSimulation(ManagerCommon manager, NaturalEnvironment naturalEnvironment, LoggerCommon logger) {
        String message = "Задали условия теперь запускаем симуляцию в бесконечном цикле.";
        System.out.println(message);
        logger.log(message);
        int step = 1;
        while (true) {
            message = "Шаг симуляции: " + step;
            logger.log(message);
            boolean flag = manager.simulate(naturalEnvironment);
            if (!flag) {
                message = "Симуляция завершена через " + step + " шагов.";
                System.out.println(message);
                logger.log(message);
                return;
            }
            step++;
        }
    }

    private static void cleanDataDirectory(LoggerCommon logger) {
        String message = "Очищаем данные.";
        System.out.println(message);
        logger.log(message);
        File file = new File(Const.DATA_DIRECTORY);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        f.delete();
                    }
                }
            }
        }
    }
}
