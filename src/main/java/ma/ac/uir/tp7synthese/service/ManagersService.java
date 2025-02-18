package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.entity.Managers;

import java.util.List;

public interface ManagersService {
    List<Managers> findAll();

    Managers findById(int theId);

    public void save(Managers manager);

    Managers update(Managers theManager);

    void deleteById(int theId);
}
