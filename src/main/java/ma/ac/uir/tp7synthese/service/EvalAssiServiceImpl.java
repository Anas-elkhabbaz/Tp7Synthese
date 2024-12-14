package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.DAO.EvalAssiRepository;
import ma.ac.uir.tp7synthese.entity.EvalAssi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EvalAssiServiceImpl implements EvalAssiService {
    private EvalAssiRepository evalAssiRepository;

    @Autowired
    public EvalAssiServiceImpl(EvalAssiRepository theEvalAssiRepository) {
        this.evalAssiRepository = theEvalAssiRepository;
    }
    @Override
    public List<EvalAssi> findAll() {
        return evalAssiRepository.findAll();
    }

    @Override
    public EvalAssi findById(int theId) {
        Optional<EvalAssi> result = evalAssiRepository.findById(theId);

        EvalAssi theEvalAssi = null;

        if (result.isPresent()) {
            theEvalAssi = result.get();
        }
        else {
            throw new RuntimeException("!find evalAssi id - " + theId);
        }
        return theEvalAssi;
    }

    @Override
    public EvalAssi save(EvalAssi theEvalAssi) {
        return evalAssiRepository.save(theEvalAssi);
    }

    @Override
    public EvalAssi update(EvalAssi theEvalAssi) {
        return evalAssiRepository.save(theEvalAssi);
    }

    @Override
    public void deleteById(int theId) {
        evalAssiRepository.deleteById(theId);
    }
}
