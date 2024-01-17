package QuixelTexel.IS.Repository.GEN.GEN;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Entity.GEN.GIM.ImmagineEntity;
import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EntitaRepository extends JpaRepository<EntitaEntity,Integer> {

    @Query
    EntitaEntity findById(int id);

    @Query
    EntitaEntity findByNomeAndUtenteEntity(String nome, UtenteEntity utenteEntity);

    @Query
    List<EntitaEntity> findAllByCartellaEntityAndUtenteEntity(CartellaEntity cartellaEntity, UtenteEntity utenteEntity);

    @Query
    List<EntitaEntity> findAllByUtenteEntity(UtenteEntity utenteEntity);

    @Query
    EntitaEntity findByImmagineEntity(ImmagineEntity immagineEntity);
}
