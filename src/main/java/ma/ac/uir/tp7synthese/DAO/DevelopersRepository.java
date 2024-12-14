package ma.ac.uir.tp7synthese.DAO;

import ma.ac.uir.tp7synthese.entity.Developers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DevelopersRepository extends JpaRepository<Developers, Integer> {

    @Query("SELECT d FROM Developers d WHERE d.login = :username AND d.password = :password")
    Developers login(@Param("username") String username, @Param("password") String password);
}
