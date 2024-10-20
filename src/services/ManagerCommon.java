package services;

import models.EcosystemObject;
import models.NaturalEnvironment;

import java.util.List;

public interface ManagerCommon {
    void update(EcosystemObject object);


    List<EcosystemObject> getObjects();

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
