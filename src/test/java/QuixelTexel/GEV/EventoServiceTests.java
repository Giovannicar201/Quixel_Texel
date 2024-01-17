package QuixelTexel.GEV;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEV.EventoEntity;
import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import QuixelTexel.IS.Exception.GEV.*;
import QuixelTexel.IS.Repository.GEV.EventoRepository;

import QuixelTexel.IS.Service.GAC.UtenteService;
import QuixelTexel.IS.Service.GEV.EventoServiceImpl;
import QuixelTexel.IS.Service.GEV.IstruzioneService;
import QuixelTexel.IS.Service.GMP.GMP.MappaService;
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
public class EventoServiceTests {

    @Mock
    private EventoRepository eventoRepository;
    @Mock
    private UtenteService utenteService;
    @Mock
    private MappaService mappaService;
    @Mock
    private IstruzioneService istruzioneService;
    @InjectMocks
    private EventoServiceImpl eventoServiceImpl;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    @SneakyThrows
    @Test
    void TC1_GEV(){

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(new MappaEntity()).when(mappaService).get(anyString());

        assertThrows(InvalidEventNameException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "", "1", "1", List.of(), List.of()));

    }

    @SneakyThrows
    @Test
    void TC2_GEV(){

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(new MappaEntity()).when(mappaService).get(anyString());

        assertThrows(InvalidEventNameException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio di una trattativa di commercio", "1", "1", List.of(), List.of()));

    }

    @SneakyThrows
    @Test
    void TC3_GEV(){

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(new EventoEntity()).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(new MappaEntity()).when(mappaService).get(anyString());

        assertThrows(NotUniqueEventException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio scontro", "1", "1", List.of(), List.of()));

    }

    @SneakyThrows
    @Test
    void TC4_GEV(){

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(new MappaEntity()).when(mappaService).get(anyString());

        assertThrows(InvalidPositionException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio scontro", null, null, List.of(), List.of()));

    }

    private void setupTC5GEV(MappaEntity mappaEntity){

        mappaEntity.setAltezza(6);
        mappaEntity.setLarghezza(7);

    }

    @SneakyThrows
    @Test
    void TC5_GEV(){

        MappaEntity mappaEntity = new MappaEntity();

        setupTC5GEV(mappaEntity);

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(mappaEntity).when(mappaService).get(anyString());

        assertThrows(InvalidPositionException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio scontro", "10", "7", List.of(), List.of()));

    }

    private void setupTC6GEV(MappaEntity mappaEntity){

        mappaEntity.setAltezza(6);
        mappaEntity.setLarghezza(6);

    }

    @SneakyThrows
    @Test
    void TC6_GEV(){

        MappaEntity mappaEntity = new MappaEntity();

        setupTC6GEV(mappaEntity);

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(new EventoEntity()).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(mappaEntity).when(mappaService).get(anyString());

        assertThrows(InvalidPositionException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio scontro", "4", "5", List.of(), List.of()));

    }

    private void setupTC12GEV(MappaEntity mappaEntity, List<String> nomiIstruzioni, List<String> valoriIstruzioni){

        mappaEntity.setAltezza(6);
        mappaEntity.setLarghezza(6);

        nomiIstruzioni.add("dialogo");
        nomiIstruzioni.add("inizio");

        valoriIstruzioni.add("Buongiorno avventuriero.");
        valoriIstruzioni.add("3");

    }

    @SneakyThrows
    @Test
    void TC12_GEV(){

        MappaEntity mappaEntity = new MappaEntity();
        List<String> nomiIstruzioni = new ArrayList<>();
        List<String> valoriIstruzioni = new ArrayList<>();

        setupTC12GEV(mappaEntity, nomiIstruzioni, valoriIstruzioni);

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(mappaEntity).when(mappaService).get(anyString());

        assertThrows(InvalidCyclesException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio baratto", "5", "5", nomiIstruzioni, valoriIstruzioni));

    }

    private void setupTC13GEV(MappaEntity mappaEntity, List<String> nomiIstruzioni, List<String> valoriIstruzioni){

        mappaEntity.setAltezza(6);
        mappaEntity.setLarghezza(6);

        nomiIstruzioni.add("dialogo");
        nomiIstruzioni.add("inizio");
        nomiIstruzioni.add("inizio");

        valoriIstruzioni.add("Buongiorno avventuriero.");
        valoriIstruzioni.add("2");
        valoriIstruzioni.add("2");

        nomiIstruzioni.add("fine");
        nomiIstruzioni.add("fine");

        valoriIstruzioni.add("fine-ciclo");
        valoriIstruzioni.add("fine-ciclo");
    }

    @SneakyThrows
    @Test
    void TC13_GEV(){

        MappaEntity mappaEntity = new MappaEntity();
        List<String> nomiIstruzioni = new ArrayList<>();
        List<String> valoriIstruzioni = new ArrayList<>();

        setupTC13GEV(mappaEntity, nomiIstruzioni, valoriIstruzioni);

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(mappaEntity).when(mappaService).get(anyString());

        assertThrows(InvalidNestedCycleException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio baratto", "5", "5", nomiIstruzioni, valoriIstruzioni));

    }

    private void setupTC16GEV(MappaEntity mappaEntity, List<String> nomiIstruzioni, List<String> valoriIstruzioni){

        mappaEntity.setAltezza(6);
        mappaEntity.setLarghezza(6);

    }

    @SneakyThrows
    @Test
    void TC16_GEV(){

        MappaEntity mappaEntity = new MappaEntity();
        List<String> nomiIstruzioni = new ArrayList<>();
        List<String> valoriIstruzioni = new ArrayList<>();

        setupTC16GEV(mappaEntity, nomiIstruzioni, valoriIstruzioni);

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(mappaEntity).when(mappaService).get(anyString());

        assertThrows(InvalidNumberOfInstructionsException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio baratto", "5", "5", nomiIstruzioni, valoriIstruzioni));

    }

    private void setupTC17GEV(MappaEntity mappaEntity, List<String> nomiIstruzioni, List<String> valoriIstruzioni){

        mappaEntity.setAltezza(6);
        mappaEntity.setLarghezza(6);

        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("testo");

        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");
        valoriIstruzioni.add("testo");

    }

    @SneakyThrows
    @Test
    void TC17_GEV(){

        MappaEntity mappaEntity = new MappaEntity();
        List<String> nomiIstruzioni = new ArrayList<>();
        List<String> valoriIstruzioni = new ArrayList<>();

        setupTC17GEV(mappaEntity, nomiIstruzioni, valoriIstruzioni);

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(mappaEntity).when(mappaService).get(anyString());

        assertThrows(InvalidNumberOfInstructionsException.class,
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio baratto", "5", "5", nomiIstruzioni, valoriIstruzioni));

    }

    private void setupTC18GEV(MappaEntity mappaEntity, List<String> nomiIstruzioni, List<String> valoriIstruzioni){

        mappaEntity.setAltezza(6);
        mappaEntity.setLarghezza(6);

        nomiIstruzioni.add("dialogo");
        nomiIstruzioni.add("inizio");
        nomiIstruzioni.add("testo");
        nomiIstruzioni.add("fine");

        valoriIstruzioni.add("Buongiorno avventuriero.");
        valoriIstruzioni.add("2");
        valoriIstruzioni.add("Mostra oggetto");
        valoriIstruzioni.add("fine-ciclo");

    }

    @SneakyThrows
    @Test
    void TC18_GEV(){

        MappaEntity mappaEntity = new MappaEntity();
        List<String> nomiIstruzioni = new ArrayList<>();
        List<String> valoriIstruzioni = new ArrayList<>();

        setupTC18GEV(mappaEntity, nomiIstruzioni, valoriIstruzioni);

        doReturn(new UtenteEntity()).when(utenteService).get(anyString());
        doReturn(null).when(eventoRepository).findByNomeAndUtenteEntity(anyString(), any(UtenteEntity.class));
        doReturn(null).when(eventoRepository).findByRigaAndColonnaAndUtenteEntity(anyString(), anyString(), any(UtenteEntity.class));
        doReturn(mappaEntity).when(mappaService).get(anyString());

        assertDoesNotThrow(
                () -> eventoServiceImpl.creaEvento("test@test.it", "Inizio baratto", "5", "5", nomiIstruzioni, valoriIstruzioni));

    }

}
