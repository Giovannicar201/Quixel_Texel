package QuixelTexel.IS.Repository.GPA.GPL;

import QuixelTexel.IS.Entity.GPA.GPL.PaletteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaletteRepository extends JpaRepository<PaletteEntity,Integer> {
}
