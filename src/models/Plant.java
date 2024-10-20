package models;

import utils.Const;

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

    public void increaseWeight(int humidity) {
        if (weight > 0) {
            weight = weight + growthRate * humidity;// примерная логика
            System.out.println(name + " растет! Вес составляет " + weight + " кг.");
        }
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int newWeight) {
        weight = newWeight;
    }

    public void decreaseWeight(int humidity) {
        if (weight > 0) {
            if (humidity >= Const.DEFAULT_HUMIDITY) {
                weight = weight - growthRate;
                System.out.println(name + " уменьшается! Вес составляет " + weight + " кг");
            } else {
                weight = weight - growthRate * humidity;
                System.out.println(name + " уменьшается! Вес составляет " + weight + " кг");
            }
        }
    }
}
