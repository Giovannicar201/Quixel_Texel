package QuixelTexel.IS.Repository.GEV;

import QuixelTexel.IS.Entity.GEV.EventoEntity;
import QuixelTexel.IS.Entity.GEV.IstruzioneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IstruzioneRepository extends JpaRepository<IstruzioneEntity, Integer> {

    @Query
    List<IstruzioneEntity> findAllByEventoEntity(EventoEntity eventoEntity);
}
