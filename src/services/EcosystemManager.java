package services;
//Управление данными

import models.Animal;
import models.EcosystemObject;
import models.Plant;

import java.io.*;
import java.util.*;

public class EcosystemManager {
    private List<EcosystemObject> objects = new ArrayList<>();
    private String filename;

    public EcosystemManager(String filename) {
        this.filename = "data/" + filename;
        loadFromFile();
    }

    public void addObject(EcosystemObject object) {
        objects.add(object);
        saveToFile();
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (EcosystemObject object : objects) {
                writer.write(object.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // todo Обдумать
                if (line.startsWith("Plant")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int growthRate = Integer.parseInt(parts[1].split(": ")[1]);
//                    Plant: 2, Growth Rate: 3, Weight: 3
                    int weight = Integer.parseInt(parts[2].split(": ")[1]);
                    objects.add(new Plant(name, growthRate, weight));
                } else if (line.startsWith("Animal")) {
                    String[] parts = line.split(", ");
                    String name = parts[0].split(": ")[1];
                    int population = Integer.parseInt(parts[1].split(": ")[1]);
                    int eating = Integer.parseInt(parts[2].split(": ")[1]);
                    int reproduction = Integer.parseInt(parts[3].split(": ")[1]);
                    objects.add(new Animal(name, population, eating, reproduction));
                }
            }
        } catch (IOException e) {
            System.out.println("Файл не найден !");
            System.out.println("Будет создан новый файл.");
        }
    }

    public List<EcosystemObject> getObjects() {
        return objects;
    }

    // todo отдельный класс для сохранения данных и интерфейсы к нему
    // todo отдельный класс для загрузки данных и интерфейсы к нему
    // здесь всё сводиться
    public void simulate(int temperature) {

        Random random = new Random();

        for (EcosystemObject object : objects) {
            if (object instanceof Plant) {
                Plant plant = (Plant) object;
                if (temperature < 45 && temperature > 5) {
                    System.out.println(plant.getName() + " растет!");
                    // Логика роста
                    plant.grow();
                } else {
                    System.out.println(plant.getName() + " не может расти из-за неблагоприятных условий.");
                    if (temperature < 5) {
                        System.out.println(plant.getName() + "Растения погибают!");
                        // уменьшается удельная масса
                    }

                }
            } else if (object instanceof Animal) {
                Animal animal = (Animal) object;
                // если кол-во еды больше чем употреблять животные то популяция растет
                // если меньше то кому не достанется те погибнуть
                // реализация по простом
                int foodAvailable = countFoodAvailable();
                int foodNeeded = countFoodNeeded();
                if (foodAvailable < foodNeeded) {
                    System.out.println(animal.getName() + " популяция уменьшается!");
                    animal.decreasePopulation(foodNeeded - foodAvailable);
                } else {
                    animal.increasePopulation();
                    System.out.println(animal.getName() + " популяция увеличивается!");
                }
            }
        }
    }

    private int countFoodNeeded() {
        int foodCount = 0;
        for (EcosystemObject object : objects) {
            if (object instanceof Animal) {
                foodCount = foodCount + ((Animal) object).getHowMuchFoodNeedForThisAnimal();
            }
        }
        return foodCount;
    }

    private int countFoodAvailable() {
        int foodCount = 0;
        for (EcosystemObject object : objects) {
            if (object instanceof Plant) {
                foodCount = foodCount + ((Plant) object).getWeight();
            }
        }
        return foodCount;
    }
}
