package models;

// + растения = имя + скорость роста
public class Plant extends EcosystemObject {
    private int growthRate;
    private int weight;

    public Plant(String name, int growthRate, int weight) {
        super(name);
        this.growthRate = growthRate;
        this.weight = weight;

    }

    @Override
    public String toString() {
        return "Plant: " + name + ", Growth Rate: " + growthRate + ", Weight: " + weight;
    }

    public void grow() {
        if (weight > 0) {
            weight = weight + growthRate;// примерная логика
            System.out.println(name + " растет!");
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int newWeight) {
        weight = newWeight;
    }
}
