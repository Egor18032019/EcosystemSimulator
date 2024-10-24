import views.MenuView;
import logging.Logger;
import logging.LoggerCommon;
import models.Animal;
import models.NaturalEnvironment;
import models.Plant;
import services.EcosystemManager;
import services.ManagerCommon;
import utils.Const;

import java.io.File;

public class EcosystemSimulator {
    public static void main(String[] args) {
        MenuView menuView = new MenuView();
        LoggerCommon logger = Logger.getLogger();

        ManagerCommon manager = null;
        NaturalEnvironment naturalEnvironment = null;

        int firstCommand = 0;

        while (firstCommand == 0) {
            menuView.showMainMenu();
            logger.log("Показываем главное меню.");
            int choice = menuView.getUserInputInt();
            menuView.getUserInputLine();

            switch (choice) {
                case 1:
                    firstCommand = 1;
                    System.out.println("Введите имя файла для симуляции:");
                    String filename = menuView.getUserInputLine();
                    logger.setFileName(filename);
                    manager = new EcosystemManager(filename, true);
                    naturalEnvironment = manager.getNaturalEnvironment();
                    break;
                case 2:
                    firstCommand = 2;
                    System.out.println("Введите имя файла для симуляции:");
                    String filenameOldSimulation = menuView.getUserInputLine();
                    logger.setFileName(filenameOldSimulation);
                    manager = new EcosystemManager(filenameOldSimulation, false);
                    naturalEnvironment = manager.getNaturalEnvironment();
                    break;
                case 3:
                    showSavedSimulation();
                    break;
                case -1:
                    cleanDataDirectory(logger);
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
                    break;
            }
        }


        while (true) {
            System.out.println("Ведите команду = номеру действия:");
            System.out.println();
            menuView.showForCreateMenu();
            logger.log("Показываем меню создания экосистемы.");
            int choice = menuView.getUserInputInt();
            menuView.getUserInputLine();

            switch (choice) {
                case 1:
                    System.out.println("Введите имя растения:");
                    String plantName = menuView.getUserInputLine();
                    System.out.println("Введите скорость роста в виде числа:");
                    int growthRate = menuView.getUserInputInt();
                    System.out.println("Введите начальный вес растения:");
                    int weight = menuView.getUserInputInt();
                    manager.update(new Plant(plantName, growthRate, weight));
                    break;
                case 2:
                    System.out.println("Введите имя животного:");
                    String animalName = menuView.getUserInputLine();
                    System.out.println("Введите начальную популяцию:");
                    int population = menuView.getUserInputInt();
                    System.out.println("Введите сколько ест за каждый тик:");
                    int eating = menuView.getUserInputInt();
                    System.out.println("Введите сколько размножается за каждый тик:");
                    int reproduction = menuView.getUserInputInt();
                    manager.update(new Animal(animalName, population, eating, reproduction));
                    break;
                case 3:
                    manager.printObjects();
                    break;
                case 4:
                    System.out.println("Введите температуру окружающей среды:");
                    naturalEnvironment.setTemperature(Integer.parseInt(menuView.getUserInputLine()));
                    manager.update(naturalEnvironment);
                    break;
                case 5:
                    System.out.println("Введите влажность окружающей среды:");
                    naturalEnvironment.setHumidity(Integer.parseInt(menuView.getUserInputLine()));
                    manager.update(naturalEnvironment);
                    break;
                case 6:
                    System.out.println("Введите кол-во доступной воды:");
                    naturalEnvironment.setWaterAvailable(Integer.parseInt(menuView.getUserInputLine()));
                    manager.update(naturalEnvironment);
                    break;
                case 0:
                    manager.save();
                    return;
                case 7:
                    runSimulation(manager, naturalEnvironment, logger);
                    break;
                case -1:
                    manager.cleanStorage();
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
                    break;
            }


        }
    }

    private static void showSavedSimulation() {

        File file = new File(Const.DATA_DIRECTORY);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                System.out.println("Список сохраненных симуляций:");
                for (File f : files) {
                    if (f.isFile()) {
                        String name = f.getName();
                        if (name.endsWith(".txt")) {
                            System.out.println(name.replace(".txt", ""));
                        }
                    }
                }
            }
        }
    }

    private static void runSimulation(ManagerCommon manager, NaturalEnvironment naturalEnvironment, LoggerCommon
            logger) {
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
