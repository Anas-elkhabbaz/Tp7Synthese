package ma.ac.uir.tp7synthese.service;

import ma.ac.uir.tp7synthese.entity.Projects;
import ma.ac.uir.tp7synthese.entity.Skills;

import java.util.List;

public interface SkillsService {
    List<Skills> findAll();

    Skills findById(int theId);

    Skills save(Skills theSkill);

    Skills update(Skills theSkill);

    void deleteById(int theId);
}
