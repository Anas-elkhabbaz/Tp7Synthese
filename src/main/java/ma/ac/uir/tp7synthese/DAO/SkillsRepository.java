package ma.ac.uir.tp7synthese.DAO;

import ma.ac.uir.tp7synthese.entity.Developers;
import ma.ac.uir.tp7synthese.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillsRepository extends JpaRepository<Skills, Integer> {

    Skills findByName(String name);
}
