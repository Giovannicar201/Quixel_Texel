package QuixelTexel.GEN;

import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Entity.GEN.GEN.ProprietaEntity;
import QuixelTexel.IS.Exception.GEN.GEN.InvalidPropertyNameException;
import QuixelTexel.IS.Exception.GEN.GEN.InvalidPropertyValueException;
import QuixelTexel.IS.Exception.GEN.GEN.NotUniquePropertyException;
import QuixelTexel.IS.Repository.GEN.GEN.ProprietaRepository;
import QuixelTexel.IS.Service.GEN.GEN.ProprietaServiceImpl;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class ProprietaServiceTests {

    @Mock
    private ProprietaRepository proprietaRepository;
    @InjectMocks
    private ProprietaServiceImpl proprietaService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    private void setupListTC8(EntitaEntity entitaEntity){

        ProprietaEntity primaProprietaEntity = new ProprietaEntity();

        primaProprietaEntity.setNome("Indice di durezza secondo la scala di Mohs");
        primaProprietaEntity.setValore("6");

        List<ProprietaEntity> proprietaEntityList = new ArrayList<>();
        proprietaEntityList.add(primaProprietaEntity);

        entitaEntity.setProprietaEntityList(proprietaEntityList);

    }

    @SneakyThrows
    @Test
    void TC8_GEN() {

        EntitaEntity entitaEntity = new EntitaEntity();

        doReturn(new ProprietaEntity()).when(proprietaRepository).findByNomeAndEntitaEntity(anyString(), any(EntitaEntity.class));

        setupListTC8(entitaEntity);

        assertThrows(InvalidPropertyNameException.class,
                () -> proprietaService.creaProprieta("Indice di durezza secondo la scala di Mohs", "6", entitaEntity));

    }

    private void setupListTC9(EntitaEntity entitaEntity){

        ProprietaEntity primaProprietaEntity = new ProprietaEntity();
        ProprietaEntity secondaProprietaEntity = new ProprietaEntity();

        primaProprietaEntity.setNome("Indice di durezza");
        primaProprietaEntity.setValore("6");

        secondaProprietaEntity.setNome("Indice di durezza");
        secondaProprietaEntity.setValore("7");

        List<ProprietaEntity> proprieta = new ArrayList<>();

        proprieta.add(primaProprietaEntity);
        proprieta.add(secondaProprietaEntity);

        entitaEntity.setProprietaEntityList(proprieta);

    }

    @SneakyThrows
    @Test
    void TC9_GEN() {

        EntitaEntity entitaEntity = new EntitaEntity();

        doReturn(new ProprietaEntity()).when(proprietaRepository).findByNomeAndEntitaEntity(anyString(), any(EntitaEntity.class));

        setupListTC9(entitaEntity);

        assertThrows(NotUniquePropertyException.class,
                () -> proprietaService.creaProprieta("Indicedidurezza", "6", entitaEntity));

    }

    private void setupListTC10(EntitaEntity entitaEntity){

        ProprietaEntity primaProprietaEntity = new ProprietaEntity();
        ProprietaEntity secondaProprietaEntity = new ProprietaEntity();

        primaProprietaEntity.setNome("Indice di durezza");
        primaProprietaEntity.setValore("6");

        secondaProprietaEntity.setNome("Origine");
        secondaProprietaEntity.setValore("Questa roccia si origina a partire da sedimenti di carbonato di calcio");

        List<ProprietaEntity> proprieta = new ArrayList<>();

        proprieta.add(primaProprietaEntity);
        proprieta.add(secondaProprietaEntity);

        entitaEntity.setProprietaEntityList(proprieta);

    }

    @SneakyThrows
    @Test
    void TC10_GEN() {

        EntitaEntity entitaEntity = new EntitaEntity();

        doReturn(null).when(proprietaRepository).findByNomeAndEntitaEntity(anyString(), any(EntitaEntity.class));

        setupListTC10(entitaEntity);

        assertThrows(InvalidPropertyValueException.class,
                () -> proprietaService.creaProprieta("Origine", "Questa roccia si origina a partire da sedimenti di carbonato di calcio", entitaEntity));

    }
}
