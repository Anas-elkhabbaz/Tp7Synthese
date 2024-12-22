package ma.ac.uir.tp7synthese.DAO;

import ma.ac.uir.tp7synthese.entity.Developers;
import ma.ac.uir.tp7synthese.entity.Managers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagersRepository extends JpaRepository<Managers, Integer> {

    @Query("SELECT d FROM Managers d WHERE d.login = :username AND d.password = :password")
    Managers login(@Param("username") String username, @Param("password") String password);

    Managers findByEmail(String email);



}
