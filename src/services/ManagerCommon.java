package services;

import models.EcosystemObject;

import java.util.List;

public interface ManagerCommon {
    void update(EcosystemObject object);


    List<EcosystemObject> getObjects();

    /**
     * Запуск симуляции.
     * @param temperature температура окружающей среды.
     * @return true, если есть живые объекты(растения или животные), иначе false
     */
    boolean simulate(int temperature);

    void printObjects();

    void save();
}
