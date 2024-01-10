package QuixelTexel.IS.Repository.GEN.GIM;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEN.GIM.ImmagineEntity;

import java.util.List;

@Repository
public interface ImmagineRepository extends JpaRepository<ImmagineEntity,Integer> {
    @Query
    ImmagineEntity findById(int id);

    @Query
    List<ImmagineEntity> findAllByUtenteEntity(UtenteEntity utenteEntity);

    @Query
    ImmagineEntity findByNomeAndUtenteEntity(String nome, UtenteEntity utenteEntity);
}
