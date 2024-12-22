package ma.ac.uir.tp7synthese.DAO;

import ma.ac.uir.tp7synthese.entity.Developers;
import ma.ac.uir.tp7synthese.entity.Managers;
import ma.ac.uir.tp7synthese.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Projects, Integer> {

    List<Projects> findByManagers(Managers managers);



}
