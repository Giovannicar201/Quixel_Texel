package unisa.it.is.project.Repository.GEN.GEN;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unisa.it.is.project.Entity.GEN.GEN.EntitaEntity;
import unisa.it.is.project.Entity.GEN.GEN.ProprietaEntity;

import java.util.List;

@Repository
public interface ProprietaRepository extends JpaRepository<ProprietaEntity,Integer> {
    @Query
    ProprietaEntity findByNomeAndEntitaEntity(String nome, EntitaEntity entitaEntity);

    @Query
    List<ProprietaEntity> findAllByEntitaEntity(EntitaEntity entitaEntity);
}
