package QuixelTexel.IS.Service.GEV;

import QuixelTexel.IS.Entity.GEV.EventoEntity;
import QuixelTexel.IS.Entity.GEV.IstruzioneEntity;
import QuixelTexel.IS.Exception.GEV.InvalidCycleValueException;
import QuixelTexel.IS.Exception.GEV.InvalidDialogValueException;
import QuixelTexel.IS.Exception.GEV.InvalidTextValueException;
import QuixelTexel.IS.Repository.GEV.IstruzioneRepository;
import QuixelTexel.IS.Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IstruzioneServiceImpl implements IstruzioneService {

    @Autowired
    private IstruzioneRepository istruzioneRepository;

    @Override
    @Transactional
    public void creaIstruzione(String nome, String valore, EventoEntity eventoEntity)
            throws InvalidDialogValueException,
            InvalidTextValueException,
            InvalidCycleValueException {

        IstruzioneEntity istruzioneEntity = new IstruzioneEntity();

        if(nome.equalsIgnoreCase("dialogo") && !Validator.isDialogValueValid(valore))
            throw new InvalidDialogValueException("ERRORE - DIALOGO NON VALIDO.");

        if(nome.equalsIgnoreCase("testo") && !Validator.isTextValueValid(valore))
            throw new InvalidTextValueException("ERRORE - TESTO NON VALIDO.");

        if(nome.equalsIgnoreCase("inizio") && !Validator.isCycleValueValid(Integer.parseInt(valore)))
            throw new InvalidCycleValueException("ERRORE - CICLO NON VALIDO.");

        istruzioneEntity.setNome(nome);
        istruzioneEntity.setValore(valore);
        istruzioneEntity.setEventoEntity(eventoEntity);

        istruzioneRepository.save(istruzioneEntity);
    }

    @Override
    @Transactional
    public String visualizzaListaIstruzioniInEvento(EventoEntity eventoEntity) {

        JSONObject istruzioniJSON = new JSONObject();
        JSONArray istruzioniArray = new JSONArray();

        List<IstruzioneEntity> istruzioni = istruzioneRepository.findAllByEventoEntity(eventoEntity);

        for (IstruzioneEntity istruzioneEntity : istruzioni) {
            JSONObject istruzioneObject = new JSONObject();
            istruzioneObject.put("nome", istruzioneEntity.getNome());
            istruzioneObject.put("valore", istruzioneEntity.getValore());
            istruzioniArray.add(istruzioneObject);
        }

        istruzioniJSON.put("nomiIstruzioni", istruzioniArray);

        return istruzioniJSON.toString();
    }
}
