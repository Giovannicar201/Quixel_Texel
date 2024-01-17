package QuixelTexel.IS.Repository.GEV;

import QuixelTexel.IS.Entity.GEV.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity,Integer> {
    @Query
    EventoEntity findByNome(String nomeEvento);

    @Query
    EventoEntity findByRigaAndColonna(String riga, String colonna);

    @Query
    void deleteByNome(String nomeEvento);

    @Query("SELECT e FROM EventoEntity e WHERE e.utenteEntity.email = :email")
    List<EventoEntity> findAllByEmail(@Param("email")String email);
}
