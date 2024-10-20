package models;

import utils.Const;

public class NaturalEnvironment extends EcosystemObject{
    // действует на всех
    int temperature;
    // влажность только на растения
    int humidity;
    // вода только на животных ?
    int waterAvailable;

    public NaturalEnvironment() {
        super("Условия окружающей среды.");
        temperature = Const.DEFAULT_TEMPERATURE;
        humidity = Const.DEFAULT_HUMIDITY;
        waterAvailable = Const.DEFAULT_WATER_AVAILABLE;
    }

    public NaturalEnvironment(int temperature, int humidity, int waterAvailable) {
        super("Условия окружающей среды.");
        this.temperature = temperature;
        this.humidity = humidity;
        this.waterAvailable = waterAvailable;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setWaterAvailable(int waterAvailable) {
        this.waterAvailable = waterAvailable;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWaterAvailable() {
        return waterAvailable;
    }

    @Override
    public String toString() {
        return "NaturalEnvironment{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", waterAvailable=" + waterAvailable +
                '}';
    }
}

