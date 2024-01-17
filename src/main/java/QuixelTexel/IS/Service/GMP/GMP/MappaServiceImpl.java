package QuixelTexel.IS.Service.GMP.GMP;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;
import QuixelTexel.IS.Exception.GEN.GEN.FolderNotFoundException;
import QuixelTexel.IS.Exception.GMP.GMP.InvalidMapHeightException;
import QuixelTexel.IS.Exception.GMP.GMP.InvalidMapNameException;
import QuixelTexel.IS.Exception.GMP.GMP.InvalidMapWidthException;
import QuixelTexel.IS.Exception.GST.InvalidColumnException;
import QuixelTexel.IS.Exception.GST.InvalidRowException;
import QuixelTexel.IS.Repository.GMP.GMP.MappaRepository;
import QuixelTexel.IS.Service.GAC.UtenteService;
import QuixelTexel.IS.Service.GEN.GEN.EntitaService;
import QuixelTexel.IS.Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Service
public class MappaServiceImpl implements MappaService {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private MappaRepository mappaRepository;
    @Autowired
    private EntitaService entitaService;

    @Override
    @Transactional
    public String crea(String email, String nome, String altezza, String larghezza)
            throws InvalidMapNameException,
            InvalidMapWidthException,
            InvalidMapHeightException {

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
    @Transactional
    public String piazza(String mappa, String nomeEntita, String riga, String colonna, String email)
            throws SQLException,
            EntityNotFoundException,
            InvalidRowException,
            InvalidColumnException,
            ParseException {

        JSONParser parser = new JSONParser();

        UtenteEntity utenteEntity = utenteService.get(email);

        MappaEntity mappaEntity = mappaRepository.findByUtenteEntity(utenteEntity);

        EntitaEntity entitaEntityQuery = entitaService.get(nomeEntita,email);

        if(entitaEntityQuery == null)
            throw new EntityNotFoundException("ERRORE - ENTITA NON ESISTENTE.");

        if(!Validator.isRowValid(riga,mappaEntity.getAltezza()))
            throw new InvalidRowException("ERRORE - RIGA NON VALIDA.");

        if(!Validator.isColumnValid(colonna,mappaEntity.getLarghezza()))
            throw new InvalidColumnException("ERRORE - COLONNA NON VALIDA.");

        JSONObject mappaJSON = (JSONObject) parser.parse(mappa);

        JSONArray entita = (JSONArray) mappaJSON.get("mappa");

        for (Object entitaOBJ : entita) {
            JSONObject entitaJSON = (JSONObject) entitaOBJ;

            if(((String)entitaJSON.get("riga")).compareTo(riga) == 0 &&
                    ((String)entitaJSON.get("colonna")).compareTo(colonna) == 0) {

                entitaJSON.put("id",entitaEntityQuery.getId());

                Blob immagine = entitaEntityQuery.getImmagineEntity().getImmagine();
                byte[] bytes = immagine.getBytes(1, (int) immagine.length());

                entitaJSON.put("immagine", Base64.getEncoder().encodeToString(bytes));
            }
        }

        mappaJSON.put("mappa",entita);

        return mappaJSON.toString();
    }

    @Override
    @Transactional
    public String rimuovi(String mappa, String riga, String colonna, String email)
            throws InvalidRowException,
            InvalidColumnException,
            ParseException {

        JSONParser parser = new JSONParser();

        UtenteEntity utenteEntity = utenteService.get(email);

        MappaEntity mappaEntity = mappaRepository.findByUtenteEntity(utenteEntity);

        if(!Validator.isRowValid(riga,mappaEntity.getAltezza()))
            throw new InvalidRowException("ERRORE - RIGA NON VALIDA.");

        if(!Validator.isColumnValid(colonna,mappaEntity.getLarghezza()))
            throw new InvalidColumnException("ERRORE - COLONNA NON VALIDA.");

        JSONObject mappaJSON = (JSONObject) parser.parse(mappa);

        JSONArray entita = (JSONArray) mappaJSON.get("mappa");

        for (Object entitaOBJ : entita) {
            JSONObject entitaJSON = (JSONObject) entitaOBJ;

            if(((String)entitaJSON.get("riga")).compareTo(riga) == 0 && ((String)entitaJSON.get("colonna")).compareTo(colonna) == 0)
                entitaJSON.put("immagine","");
        }

        mappaJSON.put("mappa",entita);

        return mappaJSON.toString();
    }

    @Override
    @Transactional
    public String visualizzaCollezioneElementi(String email, String nome)
            throws SQLException,
            FolderNotFoundException {
        return entitaService.visualizzaListaEntitaInCartella(email,nome);
    }

    @Override
    @Transactional
    public String visualizzaStatisticheMappa(String mappa) throws ParseException {

        JSONParser parser = new JSONParser();

        JSONObject statisticheJSON = new JSONObject();

        JSONObject mappaJSON = (JSONObject) parser.parse(mappa);
        JSONArray entita = (JSONArray) mappaJSON.get("mappa");

        JSONObject ultimaEntitaJSON = (JSONObject) entita.get(entita.size() - 1);

        int larghezza, altezza, numeroTotaleCelle = 0, entitaPiazzate = 0, celleVuote = 0;
        float entitaPiazzatePercentuale, celleVuotePercentuale;

        for(Object obj : entita) {
            JSONObject entitaJSON = (JSONObject) obj;

            numeroTotaleCelle++;

            int id = Math.toIntExact((Long) entitaJSON.get("id"));

            if(id > 0)
                entitaPiazzate++;
            else
                celleVuote++;
        }

        String ultimaRiga = (String) ultimaEntitaJSON.get("riga");
        String ultimaColonna = (String) ultimaEntitaJSON.get("colonna");

        larghezza = Integer.parseInt(ultimaColonna) + 1;
        altezza = Integer.parseInt(ultimaRiga) + 1;

        entitaPiazzatePercentuale = (float) (entitaPiazzate / numeroTotaleCelle) * 100;
        celleVuotePercentuale = (float) (celleVuote / numeroTotaleCelle) * 100;

        statisticheJSON.put("larghezza",larghezza + "");
        statisticheJSON.put("altezza",altezza + "");
        statisticheJSON.put("entitaPiazzate",entitaPiazzate + "");
        statisticheJSON.put("entitaPiazzatePercentuale",entitaPiazzatePercentuale +"");
        statisticheJSON.put("celleVuote",celleVuote + "");
        statisticheJSON.put("celleVuotePercentuale",celleVuotePercentuale + "");
        statisticheJSON.put("numeroTotaleCelle",numeroTotaleCelle + "");

        return statisticheJSON.toString();
    }

    @Override
    @Transactional
    public MappaEntity get(String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        return mappaRepository.findByUtenteEntity(utenteEntity);
    }
}
