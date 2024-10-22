package services;

import models.EcosystemObject;
import models.NaturalEnvironment;

import java.util.Map;

public interface ManagerCommon {
    void update(EcosystemObject object);


    Map<String, EcosystemObject> getObjects();

    /**
     * Запуск симуляции.
     * @param naturalEnvironment условия окружающей среды.
     * @return true, если есть живые объекты(растения или животные), иначе false
     */
    boolean simulate(NaturalEnvironment naturalEnvironment);

    void printObjects();

    void save();

    NaturalEnvironment getNaturalEnvironment();
}
