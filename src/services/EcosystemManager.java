package services;

import logging.Logger;
import logging.LoggerCommon;
import models.Animal;
import models.EcosystemObject;
import models.NaturalEnvironment;
import models.Plant;
import repositories.FileRepositories;
import repositories.RepositoriesCommon;

import java.util.*;

public class EcosystemManager implements ManagerCommon {
    private final List<EcosystemObject> objects;
    private final RepositoriesCommon fileRepositories;
    LoggerCommon logger = Logger.getLogger();
    NaturalEnvironment naturalEnvironment;

    public EcosystemManager(String filename) {
        fileRepositories = new FileRepositories(filename);

        objects = fileRepositories.read();
        if (objects.isEmpty()) {
            naturalEnvironment = new NaturalEnvironment();
            objects.add(naturalEnvironment);
        } else {
            // в аксиоме мы должны быть уверены,  
            //  что самый первый элемент в списке это Условия окружающей среды.
            naturalEnvironment = (NaturalEnvironment) objects.get(0);
        }
    }

    public NaturalEnvironment getNaturalEnvironment() {
        return naturalEnvironment;
    }

    @Override
    public void update(EcosystemObject object) {
        objects.add(object);
        fileRepositories.update(object);
    }

    @Override
    public List<EcosystemObject> getObjects() {
        return objects;
    }

    @Override
    public boolean simulate(NaturalEnvironment naturalEnvironment) {
        boolean isAnimalLive = true;
        boolean isPlantLive = true;
        for (EcosystemObject object : objects) {
            if (object instanceof Plant plant) {

                isPlantLive = simulateForPlant(naturalEnvironment, plant);

            } else if (object instanceof Animal animal) {
                isAnimalLive = simulateForAnimal(naturalEnvironment, animal);
            }
        }
        return isAnimalLive && isPlantLive;
    }

    private boolean simulateForAnimal(NaturalEnvironment naturalEnvironment, Animal animal) {
        int foodNeeded = countFoodNeeded();
        int temperature = naturalEnvironment.getTemperature();
        int water = naturalEnvironment.getWaterAvailable();
        boolean isWellFood = animalEatFoodAndDrinkWater(foodNeeded, animal, naturalEnvironment);
        if (!isWellFood) {
            animal.decreasePopulation();
            return false;
        } else {
            animal.increasePopulation(temperature, water);
            return true;
        }
    }

    private boolean simulateForPlant(NaturalEnvironment naturalEnvironment, Plant plant) {
        if (plant.getWeight() == 0) {
            System.out.println(plant.getName() + " не может расти.При нулевом весе.");
            logger.log(plant.getName() + " не может расти." + plant.getWeight());
            return false;
        }
        int humidity = naturalEnvironment.getHumidity();
        int temperature = naturalEnvironment.getTemperature();
        int humidityNeeded = plant.getWeight();
        if (humidity < humidityNeeded) {
            String message = "Недостаточная влажность для роста растения";
            logger.log(message);
            System.out.println(message);
            humidity = 0;
        } else {
            humidity = humidity - humidityNeeded;
        }

        naturalEnvironment.setHumidity(humidity);

        if (temperature < 45 && temperature > 5 && humidity > 0) {
            plant.increaseWeight(humidity);
        } else {
            String message = "Неблагоприятные условия для роста растения при температуре " + temperature + " и влажности " + humidity + ".";
            System.out.println(message);
            logger.log(message);
            if (temperature < 5) {
                message = plant.getName() + " вес уменьшен до" + plant.getWeight();
                System.out.println(message);
                logger.log(message);
                plant.decreaseWeight(humidity);
            } else {
                message = plant.getName() + " не может расти из-за неблагоприятных условий.";
                System.out.println(message);
                logger.log(message);
            }
        }
        return true;
    }

    private boolean animalEatFoodAndDrinkWater(int foodNeeded, Animal animal, NaturalEnvironment naturalEnvironment) {

        if (animal.getPopulation() <= 0) {
            System.out.println(animal.getName() + " не может расти.При нулевом количестве.");
            logger.log(animal.getName() + " не может расти." + animal.getPopulation());
            return true;
        }
        // Животные пьют воду по одной единицы на голову.
        // Рост животных зависит от воды.
        int waterNeeded = animal.getPopulation();
        if (naturalEnvironment.getWaterAvailable() < waterNeeded) {
            String message = "Недостаточно воды для животных " + animal.getName() + ".";
            logger.log(message);
            System.out.println(message);
            naturalEnvironment.setWaterAvailable(0);
        } else {
            naturalEnvironment.setWaterAvailable(naturalEnvironment.getWaterAvailable() - animal.getPopulation());
        }

        // оставшиеся едят растение.
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
                        logger.log(message);
                        foodCount = foodCount - weight;
                    } else {
                        foodCount = foodCount - weight;
                        String message = animal.getName() + " съел " + plant.getWeight() + " килограммов " + plant.getName();
                        System.out.println(message);
                        logger.log(message);
                        ((Plant) object).setWeight(0);
                    }
                }
            }
        }
        boolean isWellFood = foodCount <= 0;
        if (isWellFood) {
            String message = "Животные " + animal.getName() + " наелись.";
            System.out.println(message);
            logger.log(message);
        } else {
            String message = "Для животных " + animal.getName() + " не хватает еды.";
            System.out.println(message);
            logger.log(message);
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
        System.out.println(naturalEnvironment.toString());
        for (EcosystemObject obj : objects) {
            System.out.println(obj);
        }

    }

    @Override
    public void save() {
        fileRepositories.create(objects);
    }
}
