package services;
//Управление данными

import models.Animal;
import models.EcosystemObject;
import models.Plant;
import repositories.FileRepositories;
import repositories.RepositoriesCommon;

import java.util.*;

//todo создать интерфейс
public class EcosystemManager implements ManagerCommon {
    private final List<EcosystemObject> objects = new ArrayList<>();

    RepositoriesCommon fileRepositories;

    public EcosystemManager(String filename) {
        fileRepositories = new FileRepositories(objects, filename);
        fileRepositories.read();

    }

    @Override
    public void addObject(EcosystemObject object) {
        objects.add(object);
        save();
    }

    @Override
    public void save() {
        fileRepositories.create();
    }
    @Override
    public void update() {
        fileRepositories.update();
    }
    @Override
    public List<EcosystemObject> getObjects() {
        return objects;
    }

    @Override
    // здесь всё сводиться
    public boolean simulate(int temperature) {
        fileRepositories.log("Запущена симуляция для " + objects.size() + " объектов.");
        Random random = new Random();

        boolean isAnimalLive = true;
        for (EcosystemObject object : objects) {
            if (object instanceof Plant) {
                Plant plant = (Plant) object;
                if (temperature < 45 && temperature > 5) {
                    plant.increaseWeight();
                } else {
                    String message = "Неблагоприятные условия для роста растения " + plant.getName();
                    System.out.println(message);
                    fileRepositories.log(message);
                    if (temperature < 5) {
                        message = plant.getName() + " вес уменьшен до" + plant.getWeight();
                        System.out.println(message);
                        plant.decreaseWeight();
                        fileRepositories.log(message);
                    } else {
                        message = plant.getName() + " не может расти из-за неблагоприятных условий.";
                        System.out.println(message);
                        fileRepositories.log(message);
                    }
                }
            } else if (object instanceof Animal) {
                Animal animal = (Animal) object;

                int foodNeeded = countFoodNeeded();

                boolean isWellFood = animalEatFood(foodNeeded, animal);
                if (!isWellFood) {
                    animal.decreasePopulation();
                    fileRepositories.log("Животные " + animal.getName() + " умерли.");
                    isAnimalLive = false;
                } else {
                    fileRepositories.log("Популяция животных " + animal.getName() + " увеличена.");
                    animal.increasePopulation();
                }
            }
        }
        // если есть растения, то животные не умирают.
        return isAnimalLive;
    }

    private boolean animalEatFood(int foodNeeded, Animal animal) {
        if (animal.getPopulation() <= 0) {
            return false;
        }

        System.out.println("Животным " + animal.getName() + " необходимо " + foodNeeded + " килограмма еды.");
        int foodCount = foodNeeded;
        for (EcosystemObject object : objects) {
            if (object instanceof Plant) {
                if (foodCount > 0) {
                    Plant plant = (Plant) object;
                    int weight = plant.getWeight();
                    if (weight == 0) {
                        continue;
                    }
                    // рандомно потребление сортов ?
                    // может ли растение остаться в живых, а животное не наелось ?
                    if (weight > foodCount) {
                        plant.setWeight(weight - foodCount);
                        String message = animal.getName() + " съел " + foodCount + " килограммов " + plant.getName();
                        System.out.println(message);
                        fileRepositories.log(message);
                        foodCount = foodCount - weight;
                    } else {
                        foodCount = foodCount - weight;
                        String message = animal.getName() + " съел " + plant.getWeight() + " килограммов " + plant.getName();
                        System.out.println(message);
                        fileRepositories.log(message);
                        ((Plant) object).setWeight(0);
                    }
                }
            }
        }
        boolean isWellFood = foodCount <= 0;
        if (isWellFood) {
            String message = "Животные " + animal.getName() + " наелись.";
            System.out.println(message);
            fileRepositories.log(message);
        } else {
            String message = "Для животных " + animal.getName() + " не хватает еды.";
            System.out.println(message);
            fileRepositories.log(message);
        }
        return isWellFood;
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

    @Override
    public void printObjects() {
        for (EcosystemObject obj : objects) {
            System.out.println(obj);
        }
    }
}
