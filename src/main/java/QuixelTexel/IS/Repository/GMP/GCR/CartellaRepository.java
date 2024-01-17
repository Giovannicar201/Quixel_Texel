package QuixelTexel.IS.Repository.GMP.GCR;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartellaRepository extends JpaRepository<CartellaEntity,Integer> {

    @Query
    CartellaEntity findByNomeAndUtenteEntity(String nome, UtenteEntity utente);

    @Query
    List<CartellaEntity> findAllByUtenteEntity(UtenteEntity utenteEntity);

    @Query
    CartellaEntity findById(int id);
}
