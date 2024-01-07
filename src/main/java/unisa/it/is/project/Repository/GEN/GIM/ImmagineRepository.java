package unisa.it.is.project.Repository.GEN.GIM;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unisa.it.is.project.Entity.GEN.GIM.ImmagineEntity;

@Repository
public interface ImmagineRepository extends JpaRepository<ImmagineEntity,Integer> {
}
