package unisa.it.is.project.Repository.GEN.GEN;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unisa.it.is.project.Entity.GAC.UtenteEntity;
import unisa.it.is.project.Entity.GEN.GEN.EntitaEntity;
import unisa.it.is.project.Entity.GEN.GIM.ImmagineEntity;
import unisa.it.is.project.Entity.GMP.GCR.CartellaEntity;

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
