package unisa.it.is.project.Repository.GMP.GCR;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unisa.it.is.project.Entity.GMP.GCR.CartellaEntity;

@Repository
public interface CartellaRepository extends JpaRepository<CartellaEntity,Integer> {
}
