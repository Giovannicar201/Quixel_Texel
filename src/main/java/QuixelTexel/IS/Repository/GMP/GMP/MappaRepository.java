package QuixelTexel.IS.Repository.GMP.GMP;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;

@Repository
public interface MappaRepository extends JpaRepository<MappaEntity,Integer> {
    @Query
    MappaEntity findByUtenteEntity(UtenteEntity utente);

    @Modifying
    @Query("DELETE FROM MappaEntity")
    void delete();
}
