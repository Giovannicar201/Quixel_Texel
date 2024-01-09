package unisa.it.is.project.Repository.GEN.GIM;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unisa.it.is.project.Entity.GAC.UtenteEntity;
import unisa.it.is.project.Entity.GEN.GIM.ImmagineEntity;

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
