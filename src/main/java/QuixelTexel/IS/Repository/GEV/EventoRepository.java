package QuixelTexel.IS.Repository.GEV;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEV.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity,Integer> {

    @Query
    EventoEntity findByRigaAndColonnaAndUtenteEntity(String riga, String colonna, UtenteEntity utenteEntity);

    @Query
    List<EventoEntity> findAllByUtenteEntity(UtenteEntity utenteEntity);

    @Query
    EventoEntity findByNomeAndUtenteEntity(String nome,UtenteEntity utenteEntity);
}
