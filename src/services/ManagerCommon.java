package services;

import models.EcosystemObject;

import java.util.List;

public interface ManagerCommon {
    void update(EcosystemObject object);


 

    List<EcosystemObject> getObjects();

    boolean simulate(int temperature);

    void printObjects();

    void save();
}
