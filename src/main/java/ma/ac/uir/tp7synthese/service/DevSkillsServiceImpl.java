package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.DAO.DevSkillsRepository;
import ma.ac.uir.tp7synthese.entity.DevSkills;
import ma.ac.uir.tp7synthese.entity.Developers;
import ma.ac.uir.tp7synthese.entity.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DevSkillsServiceImpl implements DevSkillsService{
    private DevSkillsRepository devSkillsRepository;

    @Autowired
    public DevSkillsServiceImpl(DevSkillsRepository theDevSkillsRepository) {
        this.devSkillsRepository = theDevSkillsRepository;
    }
    @Override
    public List<DevSkills> findAll() {
        return devSkillsRepository.findAll();
    }

    @Override
    public DevSkills findById(int theId) {
        Optional<DevSkills> result = devSkillsRepository.findById(theId);

        DevSkills theDevSkills = null;

        if (result.isPresent()) {
            theDevSkills = result.get();
        }
        else {
            throw new RuntimeException("!find DevSkills id - " + theId);
        }
        return theDevSkills;
    }

    @Override
    public DevSkills save(DevSkills theDeveloper) {
        return devSkillsRepository.save(theDeveloper);
    }

    @Override
    public DevSkills update(DevSkills theDeveloper) {
        return devSkillsRepository.save(theDeveloper);
    }

    @Override
    public void deleteById(int theId) {
        devSkillsRepository.deleteById(theId);
    }

    public List<Skills> findSkillsByDeveloper(Developers developer) {
        List<DevSkills> devSkillsList = devSkillsRepository.findByDevelopers(developer);
        return devSkillsList.stream()
                .map(DevSkills::getSkills) // Extract skills from each DevSkills
                .collect(Collectors.toList());
    }
    public void deleteDevSkillsByDeveloperId(int developerId) {
        devSkillsRepository.deleteById(developerId);
    }
}
