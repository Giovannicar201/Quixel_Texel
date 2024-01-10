package QuixelTexel.IS.Service.GMP.GMP;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import QuixelTexel.IS.Exception.GMP.GMP.*;
import QuixelTexel.IS.Repository.GMP.GMP.MappaRepository;
import QuixelTexel.IS.Service.GAC.UtenteService;
import QuixelTexel.IS.Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MappaServiceImpl implements MappaService {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private MappaRepository mappaRepository;

    @Override
    @Transactional
    public String creaMappa(String email, String nome, String altezza, String larghezza)
            throws InvalidMapNameException, InvalidMapWidthException, InvalidMapHeightException {

        UtenteEntity utenteEntity = utenteService.get(email);

        MappaEntity mappaEntity = new MappaEntity();
        
        mappaRepository.delete();
        
        long larghezzaLong = Long.parseLong(larghezza);
        long altezzaLong = Long.parseLong(altezza);

        if(!Validator.isMapNameValid(nome))
            throw new InvalidMapNameException("ERRORE - NOME NON VALIDO.");

        if(!Validator.isMapWidthValid(larghezzaLong))
            throw new InvalidMapWidthException("ERRORE - LARGHEZZA NON VALIDA.");

        if(!Validator.isMapHeightValid(altezzaLong))
            throw new InvalidMapHeightException("ERRORE - ALTEZZA NON VALIDA.");

        mappaEntity.setNome(nome);
        mappaEntity.setLarghezza(larghezzaLong);
        mappaEntity.setAltezza(altezzaLong);
        mappaEntity.setUtenteEntity(utenteEntity);

        JSONObject mappaJSON = new JSONObject();
        JSONArray entita = new JSONArray();

        for(int riga = 0; riga < altezzaLong; riga++) {
            for (int colonna = 0; colonna < larghezzaLong; colonna++) {
                
                JSONObject entitaJSON = new JSONObject();

                entitaJSON.put("id",0);
                entitaJSON.put("riga",riga + "");
                entitaJSON.put("colonna",colonna + "");
                entitaJSON.put("immagine","");

                entita.add(entitaJSON);
                
            }
        }

        mappaJSON.put("mappa",entita);

        mappaRepository.save(mappaEntity);

        return mappaJSON.toString();
    }

    @Override
    public MappaEntity get(String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        return mappaRepository.findByUtenteEntity(utenteEntity);
    }
}
