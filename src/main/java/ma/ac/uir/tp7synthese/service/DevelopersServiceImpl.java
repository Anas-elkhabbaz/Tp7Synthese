package ma.ac.uir.tp7synthese.service;

import jakarta.servlet.http.HttpSession;
import ma.ac.uir.tp7synthese.DAO.DevelopersRepository;
import ma.ac.uir.tp7synthese.DAO.ManagersRepository;
import ma.ac.uir.tp7synthese.entity.DevSkills;
import ma.ac.uir.tp7synthese.entity.Developers;
import ma.ac.uir.tp7synthese.entity.Managers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DevelopersServiceImpl implements DevelopersService {
    private DevelopersRepository developersRepository;

    @Autowired
    public DevelopersServiceImpl(DevelopersRepository theDevelopersRepository) {
        this.developersRepository = theDevelopersRepository;
    }
    @Override
    public List<Developers> findAll() {
        return developersRepository.findAll();
    }

    @Override
    public Developers findById(int theId) {
        Optional<Developers> result = developersRepository.findById(theId);

        Developers theDevelopers = null;

        if (result.isPresent()) {
            theDevelopers = result.get();
        }
        else {
            throw new RuntimeException("!find Developer id - " + theId);
        }
        return theDevelopers;
    }

    @Override
    public Developers save(Developers theDeveloper) {
        return developersRepository.save(theDeveloper);
    }



    @Override
    public Developers update(Developers theDeveloper) {
        return developersRepository.save(theDeveloper);
    }

    @Override
    public void deleteById(int theId) {
        developersRepository.deleteById(theId);
    }

    public Developers getLoggedInDeveloper(HttpSession session) {
        // Récupérer l'ID du développeur depuis la session
        Integer developerId = (Integer) session.getAttribute("developerId");

        // Vérifier si l'ID existe et récupérer le développeur correspondant
        if (developerId != null) {
            return developersRepository.findById(developerId).orElse(null);
        }
        return null;
    }

    public Developers login(String username, String password,HttpSession session) {
        Developers developer = developersRepository.login(username, password);

        if (developer != null) {
            session.setAttribute("developerId", developer.getId());  // Stocke l'ID du développeur dans la session
        }
        return developer;
    }

    public Developers findByEmail(String email) {
        return developersRepository.findByEmail(email);
    }


}
