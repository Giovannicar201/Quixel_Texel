package QuixelTexel.GEN;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Entity.GEN.GIM.ImmagineEntity;
import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import QuixelTexel.IS.Exception.GEN.GEN.*;
import QuixelTexel.IS.Repository.GEN.GEN.EntitaRepository;
import QuixelTexel.IS.Service.GAC.UtenteService;
import QuixelTexel.IS.Service.GEN.GEN.EntitaServiceImpl;
import QuixelTexel.IS.Service.GEN.GEN.ProprietaService;
import QuixelTexel.IS.Service.GEN.GIM.ImmagineService;
import QuixelTexel.IS.Service.GMP.GCR.CartellaService;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class EntitaServiceTests {

    @Mock
    private UtenteService utenteService;
    @Mock
    private EntitaRepository entitaRepository;
    @Mock
    private ImmagineService immagineService;
    @Mock
    private CartellaService cartellaService;
    @Mock
    private ProprietaService proprietaService;
    @InjectMocks
    private EntitaServiceImpl entitaServiceImpl;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    @SneakyThrows
    @Test
    void TC1_GEN() {

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(immagineService).get(anyString(), anyString());
        doReturn(new EntitaEntity()).when(entitaRepository).findByImmagineEntity(any(ImmagineEntity.class));
        doReturn(new CartellaEntity()).when(cartellaService).get(anyString(), anyString());

        assertThrows(ImageNotFoundException.class,
                () -> entitaServiceImpl.creaEntita("test@test.it", "Immagine_fiore", "Fiore", "no", "Cartella decorazioni", List.of(), List.of()));

    }

    @SneakyThrows
    @Test
    void TC2_GEN() {

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(immagineService).get(anyString(), anyString());
        doReturn(new EntitaEntity()).when(entitaRepository).findByImmagineEntity(any(ImmagineEntity.class));
        doReturn(new CartellaEntity()).when(cartellaService).get(anyString(), anyString());

        assertThrows(ImageNotFoundException.class,
                () -> entitaServiceImpl.creaEntita("test@test.it", "Immagine_fiore", null, "no", "Cartella decorazioni", List.of(), List.of()));

    }

    @SneakyThrows
    @Test
    void TC3_GEN() {

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(new ImmagineEntity()).when(immagineService).get(anyString(), anyString());
        doReturn(null).when(entitaRepository).findByImmagineEntity(any(ImmagineEntity.class));
        doReturn(new CartellaEntity()).when(cartellaService).get(anyString(), anyString());

        assertThrows(InvalidEntityNameException.class,
                () -> entitaServiceImpl.creaEntita("test@test.it", "Immagine_fiore", "Fiore di colore arancione e giallo", "no", "Cartella decorazioni", List.of(), List.of()));

    }

    @SneakyThrows
    @Test
    void TC4_GEN() {

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(new ImmagineEntity()).when(immagineService).get(anyString(), anyString());
        doReturn(null).when(entitaRepository).findByImmagineEntity(any(ImmagineEntity.class));
        doReturn(new EntitaEntity()).when(entitaRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(new CartellaEntity()).when(cartellaService).get(anyString(), anyString());

        assertThrows(NotUniqueEntityException.class,
                () -> entitaServiceImpl.creaEntita("test@test.it", "Immagine_casa", "Casa", "no", "Cartella decorazioni", List.of(), List.of()));

    }

    @SneakyThrows
    @Test
    void TC5_GEN() {

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(new ImmagineEntity()).when(immagineService).get(anyString(), anyString());
        doReturn(null).when(entitaRepository).findByImmagineEntity(any(ImmagineEntity.class));
        doReturn(null).when(entitaRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(new CartellaEntity()).when(cartellaService).get(anyString(), anyString());

        assertThrows(InvalidCollisionException.class,
                () -> entitaServiceImpl.creaEntita("test@test.it", "Immagine_casa", "Casa", "", "Cartella decorazioni", List.of(), List.of()));

    }

    @SneakyThrows
    @Test
    void TC6_GEN() {

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(new ImmagineEntity()).when(immagineService).get(anyString(), anyString());
        doReturn(null).when(entitaRepository).findByImmagineEntity(any(ImmagineEntity.class));
        doReturn(null).when(entitaRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(cartellaService).get(anyString(), anyString());

        assertThrows(FolderNotFoundException.class,
                () -> entitaServiceImpl.creaEntita("test@test.it", "Immagine_casa", "Casa", "si", "Cartella decorazioni 2", List.of(), List.of()));

    }

    private void setupListTC7(List<String> proprieta, List<String> valori){

        proprieta.add("Colore pareti");
        proprieta.add("Colore tetto");
        proprieta.add("Numero finestre");
        proprieta.add("Numero porte");
        proprieta.add("Abitata");

        valori.add("Giallo");
        valori.add("Rosso");
        valori.add("4");
        valori.add("2");
        valori.add("Si");

    }

    @SneakyThrows
    @Test
    void TC7_GEN() {

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(new ImmagineEntity()).when(immagineService).get(anyString(), anyString());
        doReturn(null).when(entitaRepository).findByImmagineEntity(any(ImmagineEntity.class));
        doReturn(null).when(entitaRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(new CartellaEntity()).when(cartellaService).get(anyString(), anyString());

        List<String> proprieta = new ArrayList<>();
        List<String> valori = new ArrayList<>();

        setupListTC7(proprieta, valori);

        assertThrows(InvalidNumberOfPropertyException.class,
                () -> entitaServiceImpl.creaEntita("test@test.it", "Immagine_casa", "Casa", "si", "Cartella decorazioni", proprieta, valori));

    }

    private void setupListTC11(List<String> proprieta, List<String> valori){

        proprieta.add("Indice di durezza");
        proprieta.add("Origine");

        valori.add("6");
        valori.add("Calcarea");

    }

    @SneakyThrows
    @Test
    void TC11_GEN() {

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(new ImmagineEntity()).when(immagineService).get(anyString(), anyString());
        doReturn(null).when(entitaRepository).findByImmagineEntity(any(ImmagineEntity.class));
        doReturn(null).when(entitaRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(new CartellaEntity()).when(cartellaService).get(anyString(), anyString());

        List<String> proprieta = new ArrayList<>();
        List<String> valori = new ArrayList<>();

        setupListTC11(proprieta, valori);

        assertDoesNotThrow(
                () -> entitaServiceImpl.creaEntita("test@test.it", "Immagine_roccia", "Roccia", "si", "Cartelladecorazioni", proprieta, valori));

    }
}
