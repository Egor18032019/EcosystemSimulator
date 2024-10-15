package models;

//животные = имя + скорость поедания(зависит от температуры?) + скорость размножения(зависит от температуры?)
public class Animal extends EcosystemObject {
    private int population;
    private int eating;// сколько ест за каждый тик
    private int reproduction;// сколько размножается за каждый тик

    public Animal(String name, int population, int eating, int reproduction) {
        super(name);
        this.population = population;
        this.eating = eating;
        this.reproduction = reproduction;
    }

    @Override
    public String toString() {
        return "Animal: " + name + ", Population: " + population + ", Eating: " + eating + ", Reproduction: " + reproduction;
    }

    public int getHowMuchFoodNeedForThisAnimal() {
        return population * eating;
    }

    public void decreasePopulation(int foodMissing) {
        if (population > 0) {
            population -= foodMissing / eating;
            System.out.println(name + " популяция уменьшается! Population: " + population);
        }
    }

    public void increasePopulation() {
        if (population >= 0) {
            population = population + reproduction;
            System.out.println(name + " популяция увеличивается! Population: " + population);
        }
        // а если меньше то и расти нечему

    }

    public int getPopulation() {
        return population;
    }
}