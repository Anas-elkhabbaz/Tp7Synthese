package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.entity.Developers;
import ma.ac.uir.tp7synthese.entity.EvalAssi;

import java.util.List;

public interface EvalAssiService {
    List<EvalAssi> findAll();

    EvalAssi findById(int theId);

    EvalAssi save(EvalAssi theDeveloper);

    EvalAssi update(EvalAssi theDeveloper);

    void deleteById(int theId);
}
