package QuixelTexel.IS.Repository.GPA.GPL;

import QuixelTexel.IS.Entity.GPA.GPL.ColoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColoreRepository extends JpaRepository<ColoreEntity,Integer> {
}
