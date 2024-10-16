package services;
//Управление данными

import models.Animal;
import models.EcosystemObject;
import models.Plant;
import repositories.FileRepositories;
import repositories.RepositoriesCommon;

import java.util.*;

public class EcosystemManager {
    private final List<EcosystemObject> objects = new ArrayList<>();

    RepositoriesCommon fileRepositories;

    public EcosystemManager(String filename) {
        fileRepositories = new FileRepositories(objects, filename);
        fileRepositories.load();
    }

    public void addObject(EcosystemObject object) {
        objects.add(object);
        fileRepositories.save();
    }

    public void save() {
        fileRepositories.save();
    }

    public List<EcosystemObject> getObjects() {
        return objects;
    }


    // здесь всё сводиться
    public boolean simulate(int temperature) {

        Random random = new Random();

        boolean isAnimalLive = true;
        for (EcosystemObject object : objects) {
            if (object instanceof Plant) {
                Plant plant = (Plant) object;
                if (temperature < 45 && temperature > 5) {
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

                int foodNeeded = countFoodNeeded();

                boolean isWellFood = animalEatFood(foodNeeded, animal);
                if (!isWellFood) {
                    animal.decreasePopulation();
                    isAnimalLive = false;
                } else {


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
                    // рандомно потребление сортов ?
                    // может ли растение остаться в живых, а животное не наелось ?
                    if (weight > foodCount) {
                        plant.setWeight(weight - foodCount);
                        System.out.println(animal.getName() + " съел " + foodCount + " килограммов " + plant.getName());
                        foodCount = foodCount - weight;
                    } else {
                        foodCount = foodCount - weight;
                        System.out.println(animal.getName() + " съел " + plant.getWeight() + " килограммов " + plant.getName());
                        ((Plant) object).setWeight(0);
                    }
                }
            }
        }
        boolean isWellFood = foodCount <= 0;
        if (isWellFood) {
            System.out.println("Животные " + animal.getName() + " наелись.");
        } else {
            System.out.println("Животные " + animal.getName() + " не хватают еды.");
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

    public void printObjects() {
        for (EcosystemObject obj : objects) {
            System.out.println(obj);
        }
    }
}
