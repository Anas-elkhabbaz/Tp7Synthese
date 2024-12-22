package ma.ac.uir.tp7synthese.DAO;

import ma.ac.uir.tp7synthese.entity.DevSkills;
import ma.ac.uir.tp7synthese.entity.Developers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevSkillsRepository extends JpaRepository<DevSkills, Integer> {
    List<DevSkills> findByDevelopers(Developers developers);

    void deleteById(int id);


}
