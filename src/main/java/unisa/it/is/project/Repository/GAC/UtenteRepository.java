package unisa.it.is.project.Repository.GAC;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unisa.it.is.project.Entity.GAC.UtenteEntity;

@Repository
public interface UtenteRepository extends JpaRepository<UtenteEntity,String> {
}
