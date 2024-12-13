package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.entity.Developers;

import java.util.List;

public interface DevelopersService {
    List<Developers> findAll();

    Developers findById(int theId);

    Developers save(Developers theDeveloper);

    Developers update(Developers theDeveloper);

    void deleteById(int theId);
}
