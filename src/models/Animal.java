package models;

//животные = имя + скорость поедания(зависит от температуры?) + скорость размножения(зависит от температуры?)
public class Animal extends EcosystemObject {
    private int population;
    private final int eating;// сколько ест за каждый тик
    private final int reproduction;// сколько размножается за каждый тик

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

    public void decreasePopulation() {
        //сразу все растения съедают и потом все сразу умирают ? или потихоньку умирают?
        // или какая-то другая логика ?
        // или умирает часть животных, что бы популяция осталась ?
        if (population > 0) {
            System.out.println("Погибло от голода " + population + " " + name);
            population = 0;
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