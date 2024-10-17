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

    public void increaseWeight() {
        if (weight > 0) {
            weight = weight + growthRate;// примерная логика
            System.out.println(name + " растет! Вес составляет " + weight + " кг");
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int newWeight) {
        weight = newWeight;
    }

    public void decreaseWeight() {
        if (weight > 0) {
            weight = weight - growthRate;
            System.out.println(name + " уменьшается! Вес составляет " + weight + " кг");
        }
    }
}
