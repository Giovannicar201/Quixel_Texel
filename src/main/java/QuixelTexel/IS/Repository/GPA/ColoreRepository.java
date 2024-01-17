package QuixelTexel.IS.Repository.GPA;

import QuixelTexel.IS.Entity.GPA.ColoreEntity;
import QuixelTexel.IS.Entity.GPA.PaletteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ColoreRepository extends JpaRepository<ColoreEntity,Integer> {

    @Query
    List<ColoreEntity> findAllByPaletteEntity(PaletteEntity paletteEntity);
}
