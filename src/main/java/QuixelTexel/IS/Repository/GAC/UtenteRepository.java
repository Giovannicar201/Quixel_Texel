package QuixelTexel.IS.Repository.GAC;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import QuixelTexel.IS.Entity.GAC.UtenteEntity;

@Repository
public interface UtenteRepository extends JpaRepository<UtenteEntity,String> {
    @Query
    UtenteEntity findByEmail(String email);
}
