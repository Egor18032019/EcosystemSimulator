package services;

import models.EcosystemObject;

import java.util.List;

public interface ManagerCommon {
    void addObject(EcosystemObject object);

    void save();
    void update();

    List<EcosystemObject> getObjects();

    boolean simulate(int temperature);

    void printObjects();
}
