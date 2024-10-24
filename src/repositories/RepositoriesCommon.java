package repositories;

import models.EcosystemObject;

import java.util.Map;

public interface RepositoriesCommon {


    void create(Map<String, EcosystemObject> objects);

    Map<String, EcosystemObject> read();
//    List<EcosystemObject> read();


    void update(EcosystemObject object);

    void delete();


    void save(Map<String, EcosystemObject> storage);
}
