import models.Animal;
import models.EcosystemObject;
import models.Plant;
import services.EcosystemManager;

import java.util.Scanner;

public class EcosystemSimulator {
    public static void main(String[] args) {
        int temperature = 20;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя файла для симуляции:");
        String filename = scanner.nextLine();
        EcosystemManager manager = new EcosystemManager(filename);


        while (true) {
            System.out.println("Ведите команду = номеру действия:");

            System.out.println("1. Добавить растение");
            System.out.println("2. Добавить животное");
            System.out.println("3. Показать объекты");
            System.out.println("4. Введите температуру окружающей среды. По умолчанию = 20");
            //todo добавить в экосистему влажность, количество доступной воды
            System.out.println("5. Запустить симуляцию");
            System.out.println("0. Выход");
            //todo добавить  очистить данные
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Введите имя растения:");
                    String plantName = scanner.nextLine();
                    System.out.println("Введите скорость роста в виде числа:");
                    // то есть вводят 1 значит +1 за каждый шаг.
                    int growthRate = scanner.nextInt();
                    System.out.println("Введите начальный вес растения:");
                    int weight = scanner.nextInt();
                    manager.addObject(new Plant(plantName, growthRate, weight));
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
                    manager.addObject(new Animal(animalName, population, eating, reproduction));
                    break;
                case 3:
                    manager.printObjects();
                    break;
                case 4:
                    System.out.println("Введите имя температуры окружающей среды:");
                    temperature = Integer.parseInt(scanner.nextLine());
                    //todo отдельный класс для окружающей среды.?
                    break;
                case 0:
                    manager.save();
                    return;
                case 5:
                    System.out.println("Задали условия теперь запускаем симуляцию в цикле на 10 шагов.");
                    runSimulation(manager, temperature);
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
                    break;
            }


        }
    }

    private static void runSimulation(EcosystemManager manager, int temperature) {
        // 10 шагов симуляции
        for (int step = 1; step <= 10; step++) {
// в зависимости от температуры изменяем кол-во животных и растений
//                температурный коэффициент для животных и растений одинаковый или разный ?
            boolean flag = manager.simulate(temperature);
            if (!flag) {
                System.out.println("Симуляция завершена через " + step + " шагов.");
                return;
            }
        }
    }

}
