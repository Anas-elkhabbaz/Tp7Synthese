package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.DAO.ProjectsRepository;
import ma.ac.uir.tp7synthese.DAO.SkillsRepository;
import ma.ac.uir.tp7synthese.entity.Developers;
import ma.ac.uir.tp7synthese.entity.Projects;
import ma.ac.uir.tp7synthese.entity.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SkillsServiceImpl implements SkillsService {
    private SkillsRepository skillsRepository;

    @Autowired
    public SkillsServiceImpl(SkillsRepository theSkillsRepository) {
        this.skillsRepository = theSkillsRepository;
    }

    @Override
    public List<Skills> findAll() {
        return skillsRepository.findAll();
    }

    @Override
    public Skills findById(int theId) {
        Optional<Skills> result = skillsRepository.findById(theId);

        Skills theSkills = null;

        if (result.isPresent()) {
            theSkills = result.get();
        }
        else {
            throw new RuntimeException("!find skill id - " + theId);
        }
        return theSkills;    }

    @Override
    public Skills save(Skills theSkills) {
        return skillsRepository.save(theSkills);
    }

    @Override
    public Skills update(Skills theSkills) {
        return skillsRepository.save(theSkills);
    }

    @Override
    public void deleteById(int theId) {
        skillsRepository.deleteById(theId);
    }
    public Skills findByName(String name) {
        return skillsRepository.findByName(name);
    }

}
