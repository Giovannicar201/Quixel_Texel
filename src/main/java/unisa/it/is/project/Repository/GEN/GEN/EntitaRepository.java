package unisa.it.is.project.Repository.GEN.GEN;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unisa.it.is.project.Entity.GEN.GEN.EntitaEntity;

@Repository
public interface EntitaRepository extends JpaRepository<EntitaEntity,Integer> {
}
