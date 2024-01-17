package QuixelTexel.GEV;

import QuixelTexel.IS.Entity.GEV.EventoEntity;
import QuixelTexel.IS.Entity.GEV.IstruzioneEntity;
import QuixelTexel.IS.Exception.GEV.InvalidCycleValueException;
import QuixelTexel.IS.Exception.GEV.InvalidDialogValueException;
import QuixelTexel.IS.Exception.GEV.InvalidTextValueException;
import QuixelTexel.IS.Repository.GEV.IstruzioneRepository;
import QuixelTexel.IS.Service.GEV.IstruzioneServiceImpl;
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

@SpringBootTest
public class IstruzioneServiceTests {

    @Mock
    private IstruzioneRepository istruzioneRepository;
    @InjectMocks
    private IstruzioneServiceImpl istruzioneServiceImpl;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    private void setupTC7GEV(EventoEntity eventoEntity, List<IstruzioneEntity> istruzioneEntityList){

        IstruzioneEntity primaIstruzioneEntity = new IstruzioneEntity();
        IstruzioneEntity secondaIstruzioneEntity = new IstruzioneEntity();

        primaIstruzioneEntity.setNome("dialogo");

        secondaIstruzioneEntity.setNome("");

        istruzioneEntityList.add(primaIstruzioneEntity);
        istruzioneEntityList.add(secondaIstruzioneEntity);

        eventoEntity.setIstruzioneEntityList(istruzioneEntityList);

    }

    @SneakyThrows
    @Test
    void TC7_GEV(){

        EventoEntity eventoEntity = new EventoEntity();
        List<IstruzioneEntity> istruzioneEntityList = new ArrayList<>();

        setupTC7GEV(eventoEntity, istruzioneEntityList);

        assertThrows(InvalidDialogValueException.class,
                () -> istruzioneServiceImpl.creaIstruzione("dialogo", "", eventoEntity));

    }

    private void setupTC8GEV(EventoEntity eventoEntity, List<IstruzioneEntity> istruzioneEntityList){

        IstruzioneEntity primaIstruzioneEntity = new IstruzioneEntity();
        IstruzioneEntity secondaIstruzioneEntity = new IstruzioneEntity();

        primaIstruzioneEntity.setNome("dialogo");

        secondaIstruzioneEntity.setNome("Buongiorno avventuriero. Voglio offrirti qualcosa.");

        istruzioneEntityList.add(primaIstruzioneEntity);
        istruzioneEntityList.add(secondaIstruzioneEntity);

        eventoEntity.setIstruzioneEntityList(istruzioneEntityList);

    }

    @SneakyThrows
    @Test
    void TC8_GEV(){

        EventoEntity eventoEntity = new EventoEntity();
        List<IstruzioneEntity> istruzioneEntityList = new ArrayList<>();

        setupTC8GEV(eventoEntity, istruzioneEntityList);

        assertThrows(InvalidDialogValueException.class,
                () -> istruzioneServiceImpl.creaIstruzione("dialogo", "Buongiorno avventuriero. Voglio offrirti qualcosa.", eventoEntity));

    }

    private void setupTC9GEV(EventoEntity eventoEntity, List<IstruzioneEntity> istruzioneEntityList){

        IstruzioneEntity primaIstruzioneEntity = new IstruzioneEntity();
        IstruzioneEntity secondaIstruzioneEntity = new IstruzioneEntity();

        primaIstruzioneEntity.setNome("dialogo");
        primaIstruzioneEntity.setValore("inizio");

        secondaIstruzioneEntity.setNome("Buongiorno avventuriero.");
        secondaIstruzioneEntity.setValore(null);

        istruzioneEntityList.add(primaIstruzioneEntity);
        istruzioneEntityList.add(secondaIstruzioneEntity);

        eventoEntity.setIstruzioneEntityList(istruzioneEntityList);

    }

    @SneakyThrows
    @Test
    void TC9_GEV(){

        EventoEntity eventoEntity = new EventoEntity();
        List<IstruzioneEntity> istruzioneEntityList = new ArrayList<>();

        setupTC9GEV(eventoEntity, istruzioneEntityList);

        assertThrows(NumberFormatException.class,
                () -> istruzioneServiceImpl.creaIstruzione("inizio", null, eventoEntity));

    }

    private void setupTC10GEV(EventoEntity eventoEntity, List<IstruzioneEntity> istruzioneEntityList){

        IstruzioneEntity primaIstruzioneEntity = new IstruzioneEntity();
        IstruzioneEntity secondaIstruzioneEntity = new IstruzioneEntity();

        primaIstruzioneEntity.setNome("dialogo");
        primaIstruzioneEntity.setValore("inizio");

        secondaIstruzioneEntity.setNome("Buongiorno avventuriero.");
        secondaIstruzioneEntity.setValore("-1");

        istruzioneEntityList.add(primaIstruzioneEntity);
        istruzioneEntityList.add(secondaIstruzioneEntity);

        eventoEntity.setIstruzioneEntityList(istruzioneEntityList);

    }

    @SneakyThrows
    @Test
    void TC10_GEV(){

        EventoEntity eventoEntity = new EventoEntity();
        List<IstruzioneEntity> istruzioneEntityList = new ArrayList<>();

        setupTC10GEV(eventoEntity, istruzioneEntityList);

        assertThrows(InvalidCycleValueException.class,
                () -> istruzioneServiceImpl.creaIstruzione("inizio", "-1", eventoEntity));

    }

    private void setupTC11GEV(EventoEntity eventoEntity, List<IstruzioneEntity> istruzioneEntityList){

        IstruzioneEntity primaIstruzioneEntity = new IstruzioneEntity();
        IstruzioneEntity secondaIstruzioneEntity = new IstruzioneEntity();

        primaIstruzioneEntity.setNome("dialogo");
        primaIstruzioneEntity.setValore("inizio");

        secondaIstruzioneEntity.setNome("Buongiorno avventuriero.");
        secondaIstruzioneEntity.setValore("5");

        istruzioneEntityList.add(primaIstruzioneEntity);
        istruzioneEntityList.add(secondaIstruzioneEntity);

        eventoEntity.setIstruzioneEntityList(istruzioneEntityList);

    }

    @SneakyThrows
    @Test
    void TC11_GEV(){

        EventoEntity eventoEntity = new EventoEntity();
        List<IstruzioneEntity> istruzioneEntityList = new ArrayList<>();

        setupTC11GEV(eventoEntity, istruzioneEntityList);

        assertThrows(InvalidCycleValueException.class,
                () -> istruzioneServiceImpl.creaIstruzione("inizio", "5", eventoEntity));

    }

    private void setupTC14GEV(EventoEntity eventoEntity, List<IstruzioneEntity> istruzioneEntityList){

        IstruzioneEntity primaIstruzioneEntity = new IstruzioneEntity();
        IstruzioneEntity secondaIstruzioneEntity = new IstruzioneEntity();

        primaIstruzioneEntity.setNome("dialogo");
        primaIstruzioneEntity.setValore("inizio");
        primaIstruzioneEntity.setValore("testo");
        primaIstruzioneEntity.setValore("fine");


        secondaIstruzioneEntity.setNome("Buongiorno avventuriero.");
        secondaIstruzioneEntity.setValore("2");
        secondaIstruzioneEntity.setValore(null);
        secondaIstruzioneEntity.setValore("fine-ciclo");

        istruzioneEntityList.add(primaIstruzioneEntity);
        istruzioneEntityList.add(secondaIstruzioneEntity);

        eventoEntity.setIstruzioneEntityList(istruzioneEntityList);

    }

    @SneakyThrows
    @Test
    void TC14_GEV(){

        EventoEntity eventoEntity = new EventoEntity();
        List<IstruzioneEntity> istruzioneEntityList = new ArrayList<>();

        setupTC14GEV(eventoEntity, istruzioneEntityList);

        assertThrows(InvalidTextValueException.class,
                () -> istruzioneServiceImpl.creaIstruzione("testo", null, eventoEntity));

    }

    private void setupTC15GEV(EventoEntity eventoEntity, List<IstruzioneEntity> istruzioneEntityList){

        IstruzioneEntity primaIstruzioneEntity = new IstruzioneEntity();
        IstruzioneEntity secondaIstruzioneEntity = new IstruzioneEntity();

        primaIstruzioneEntity.setNome("dialogo");
        primaIstruzioneEntity.setValore("inizio");
        primaIstruzioneEntity.setValore("testo");
        primaIstruzioneEntity.setValore("fine");

        secondaIstruzioneEntity.setNome("Buongiorno avventuriero.");
        secondaIstruzioneEntity.setValore("2");
        secondaIstruzioneEntity.setValore("In questa bottega potrai trovare ogni tipo di materiale raro");
        secondaIstruzioneEntity.setValore("fine-ciclo");

        istruzioneEntityList.add(primaIstruzioneEntity);
        istruzioneEntityList.add(secondaIstruzioneEntity);

        eventoEntity.setIstruzioneEntityList(istruzioneEntityList);

    }

    @SneakyThrows
    @Test
    void TC15_GEV(){

        EventoEntity eventoEntity = new EventoEntity();
        List<IstruzioneEntity> istruzioneEntityList = new ArrayList<>();

        setupTC15GEV(eventoEntity, istruzioneEntityList);

        assertThrows(InvalidTextValueException.class,
                () -> istruzioneServiceImpl.creaIstruzione("testo", "In questa bottega potrai trovare ogni tipo di materiale raro", eventoEntity));

    }

}
