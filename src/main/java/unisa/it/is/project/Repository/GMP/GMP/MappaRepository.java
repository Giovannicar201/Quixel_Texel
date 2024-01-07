package unisa.it.is.project.Repository.GMP.GMP;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unisa.it.is.project.Entity.GAC.UtenteEntity;
import unisa.it.is.project.Entity.GMP.GMP.MappaEntity;

@Repository
public interface MappaRepository extends JpaRepository<MappaEntity,Integer> {
    @Query
    MappaEntity findByUtenteEntity(UtenteEntity utente);

    @Modifying
    @Query("DELETE FROM MappaEntity")
    void delete();
}
