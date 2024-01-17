package QuixelTexel.IS.Repository.GPA;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GPA.PaletteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaletteRepository extends JpaRepository<PaletteEntity,Integer> {

    @Query
    PaletteEntity findByNomePalette(String nomePalette);

    @Query
    PaletteEntity findByNomePaletteAndUtenteEntity(String nomePalette, UtenteEntity utente);

    @Query
    List<PaletteEntity> getAllByUtenteEntity(UtenteEntity utenteEntity);
}
