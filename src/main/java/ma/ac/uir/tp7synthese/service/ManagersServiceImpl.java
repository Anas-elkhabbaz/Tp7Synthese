package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.DAO.ManagersRepository;
import ma.ac.uir.tp7synthese.DAO.ProjectsRepository;
import ma.ac.uir.tp7synthese.entity.Managers;
import ma.ac.uir.tp7synthese.entity.Projects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagersServiceImpl implements ManagersService {
    private ManagersRepository managersRepository;

    @Autowired
    public ManagersServiceImpl(ManagersRepository theManagersRepository) {
        this.managersRepository = theManagersRepository;
    }
    @Override
    public List<Managers> findAll() {
        return managersRepository.findAll();
    }

    @Override
    public Managers findById(int theId) {
        Optional<Managers> result = managersRepository.findById(theId);

        Managers theManagers = null;

        if (result.isPresent()) {
            theManagers = result.get();
        }
        else {
            throw new RuntimeException("!find manager id - " + theId);
        }
        return theManagers;
    }

    @Override
    public Managers save(Managers theManager) {
        return managersRepository.save(theManager);
    }

    @Override
    public Managers update(Managers theManager) {
        return managersRepository.save(theManager);
    }

    @Override
    public void deleteById(int theId) {
        managersRepository.deleteById(theId);
    }
}
