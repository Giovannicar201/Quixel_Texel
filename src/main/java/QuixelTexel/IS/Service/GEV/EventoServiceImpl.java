package QuixelTexel.IS.Service.GEV;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEV.EventoEntity;
import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import QuixelTexel.IS.Exception.GEV.*;
import QuixelTexel.IS.Repository.GEV.EventoRepository;
import QuixelTexel.IS.Service.GAC.UtenteService;
import QuixelTexel.IS.Service.GMP.GMP.MappaService;
import QuixelTexel.IS.Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private MappaService mappaService;
    @Autowired
    private IstruzioneService istruzioneService;

    @Override
    @Transactional
    public void creaEvento(String email, String nome, String riga, String colonna, List<String> nomiIstruzioni, List<String> valoriIstruzioni)
            throws InvalidEventNameException,
            NotUniqueEventException,
            InvalidNumberOfInstructionsException,
            InvalidPositionException,
            InvalidNestedCycleException,
            InvalidCyclesException,
            InvalidCycleValueException,
            InvalidTextValueException,
            InvalidDialogValueException {

        UtenteEntity utenteEntity = utenteService.get(email);

        EventoEntity eventoEntity = new EventoEntity();

        EventoEntity eventoEntityQuery = eventoRepository.findByNomeAndUtenteEntity(nome,utenteEntity);
        EventoEntity eventoEntitySecondQuery = eventoRepository.findByRigaAndColonnaAndUtenteEntity(riga,colonna,utenteEntity);

        MappaEntity mappaEntity = mappaService.get(email);

        int numeroIstruzioniInizioCiclo = 0;
        int numeroIstruzioniFineCiclo = 0;

        if(!Validator.isEventNameValid(nome))
            throw new InvalidEventNameException("ERRORE - NOME NON VALIDO.");

        if(eventoEntityQuery != null)
            throw new NotUniqueEventException("ERRORE - EVENTO GIÀ ESISTENTE.");

        if(!Validator.isPositionValid(riga,colonna,mappaEntity.getLarghezza(),mappaEntity.getAltezza()))
            throw new InvalidPositionException("ERRORE - POSIZIONE NON VALIDA");

        if(eventoEntitySecondQuery != null)
            throw new InvalidPositionException("ERRORE - POSIZIONE NON VALIDA");

        if(!Validator.isNumberOfInstructionValid(nomiIstruzioni.size()))
            throw new InvalidNumberOfInstructionsException("ERRORE - NUMERO DI ISTRUZIONI NON VALIDO.");

        Iterator<String> iteratoreNomi = nomiIstruzioni.iterator();
        Iterator<String> iteratoreValori = valoriIstruzioni.iterator();


        while (iteratoreNomi.hasNext()) {

            String nomeIstruzione = iteratoreNomi.next();

            if (nomeIstruzione.equalsIgnoreCase("inizio"))
                numeroIstruzioniInizioCiclo++;

            if (nomeIstruzione.equalsIgnoreCase("fine")) {

                numeroIstruzioniFineCiclo++;

                if(numeroIstruzioniInizioCiclo != numeroIstruzioniFineCiclo)
                    throw new InvalidNestedCycleException("ERRORE - CICLO ANNIDATO NON VALIDO.");
            }
        }

        if(numeroIstruzioniInizioCiclo != numeroIstruzioniFineCiclo)
            throw new InvalidCyclesException("ERRORE - NUMERO DI ISTRUZIONI INIZIO CICLO E FINE CICLO NON VALIDO.");

        iteratoreNomi = nomiIstruzioni.iterator();

        while (iteratoreNomi.hasNext() && iteratoreValori.hasNext()) {

            String nomeIstruzione = iteratoreNomi.next();
            String valoreIstruzione = iteratoreValori.next();

            istruzioneService.creaIstruzione(nomeIstruzione,valoreIstruzione,eventoEntity);
        }

        eventoEntity.setUtenteEntity(utenteEntity);
        eventoEntity.setMappaEntity(mappaEntity);
        eventoEntity.setNome(nome);
        eventoEntity.setRiga(riga);
        eventoEntity.setColonna(colonna);

        eventoRepository.save(eventoEntity);
    }

    @Override
    @Transactional
    public EventoEntity get(String nomeEvento, String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        return eventoRepository.findByNomeAndUtenteEntity(nomeEvento, utenteEntity);
    }

    @Override
    @Transactional
    public String visualizzaListaEventi(String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        JSONObject eventiJSON = new JSONObject();
        JSONArray nomiEventi = new JSONArray();

        List<EventoEntity> eventi = eventoRepository.findAllByUtenteEntity(utenteEntity);

        for (EventoEntity eventoEntity : eventi)
            nomiEventi.add(eventoEntity.getNome());

        eventiJSON.put("nomiEventi", nomiEventi);

        return eventiJSON.toString();
    }
}
