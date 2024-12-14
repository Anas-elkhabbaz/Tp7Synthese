package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.entity.DevSkills;

import java.util.List;

public interface DevSkillsService {
    List<DevSkills> findAll();

    DevSkills findById(int theId);

    DevSkills save(DevSkills theDevSkills);

    DevSkills update(DevSkills theDevSkills);

    void deleteById(int theId);
}
