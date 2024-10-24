package views;

import java.util.Scanner;

public class MenuView {
    private final Scanner scanner = new Scanner(System.in);

    public void showMainMenu() {
        System.out.println("1. Создать новую экосистему.");
        System.out.println("2. Выбрать сохраненную экосистему.");
        System.out.println("3. Показать сохраненные данные симуляций.");
        System.out.println("-1. Очистить данные.");
    }

    public void showForCreateMenu() {
        System.out.println("1. Добавить растение.");
        System.out.println("2. Добавить животное.");
        System.out.println("3. Показать данные симуляции.");
        System.out.println("4. Ввести температуру окружающей среды.");
        System.out.println("5. Ввести влажность окружающей среды.");
        System.out.println("6. Ввести кол-во доступной воды.");
        System.out.println("7. Запустить симуляцию.");
        System.out.println("0. Выход.");
        System.out.println("-1. Очистить данные текущей симуляции.");
    }

    public int getUserInputInt() {
        return scanner.nextInt();
    }

    public String getUserInputLine() {
        return scanner.nextLine().trim();
    }
}
