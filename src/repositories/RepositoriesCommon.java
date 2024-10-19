package repositories;

import models.EcosystemObject;

import java.util.List;

public interface RepositoriesCommon {


    void create(List<EcosystemObject> objects);

    List<EcosystemObject> read();



    void update(EcosystemObject object);

    void delete();


}
