package ma.ac.uir.tp7synthese.DAO;

import ma.ac.uir.tp7synthese.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepository extends JpaRepository<Projects, Integer> {
}
