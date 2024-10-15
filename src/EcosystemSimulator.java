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
            System.out.println("4. Введите температуру окружающей среды. По умолчанию = 20");
            System.out.println("1. Добавить растение");
            System.out.println("2. Добавить животное");
            System.out.println("3. Показать объекты");
            System.out.println("0. Выход");
            System.out.println("5. Запустить симуляцию");
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
                    System.out.println("Введите популяцию:");
                    int population = scanner.nextInt();
                    System.out.println("Введите сколько ест за каждый тик:");
                    int eating = scanner.nextInt();
                    System.out.println("Введите сколько размножается за каждый тик:");
                    int reproduction = scanner.nextInt();
                    manager.addObject(new Animal(animalName, population, eating, reproduction));
                    break;
                case 3:
                    for (EcosystemObject obj : manager.getObjects()) {
                        System.out.println(obj);
                    }
                    break;
                case 4:
                    System.out.println("Введите имя температуры окружающей среды:");
                    temperature = Integer.parseInt(scanner.nextLine());
                    //todo отдельный класс для окружающей среды.
                    break;
                case 0:
                    return;
                case 5:
                    System.out.println("Задали условия теперь запускаем симуляцию в цикле на 10 шагов.");
                    runSimulation(manager, temperature);
                    break;
                default:    // если введен неверный вариант
                    System.out.println("Неверный выбор. Попробуйте еще раз.");
                    break;// выходим из цикла
            }


        }
    }

    private static void runSimulation(EcosystemManager manager, int temperature) {
        for (int step = 0; step < 10; step++) {
            // 10 шагов симуляции
// в зависимости от температуры изменяем кол-во животных и растений
//                температурный коэффициент для животных и растений одинаковый или разный ?
            manager.simulate(temperature);
        }
    }

}
