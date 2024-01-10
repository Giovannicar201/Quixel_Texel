package QuixelTexel.IS.Service.GMP.GST;

import QuixelTexel.IS.Entity.GEN.GEN.CoordinateEntity;
import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import QuixelTexel.IS.Exception.GEN.GEN.*;
import QuixelTexel.IS.Exception.GMP.GST.InvalidColumnException;
import QuixelTexel.IS.Exception.GMP.GST.InvalidRowException;
import QuixelTexel.IS.Service.GEN.GEN.EntitaService;
import QuixelTexel.IS.Service.GMP.GMP.MappaService;
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
public class MatitaServiceMappaImpl implements MatitaService {

    @Autowired
    private EntitaService entitaService;
    @Autowired
    private MappaService mappaService;

    @Override
    @Transactional
    public String piazza(String email, String mappa, String nome, String riga, String colonna) throws ParseException, EntityNotFoundException, InvalidColumnException, InvalidRowException, SQLException {

        EntitaEntity entitaEntityQuery = entitaService.get(nome,email);

        MappaEntity mappaEntity = mappaService.get(email);

        JSONParser parser = new JSONParser();

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

                entitaJSON.put("immagine",Base64.getEncoder().encodeToString(bytes));
            }
        }

        mappaJSON.put("mappa",entita);

        CoordinateEntity coordinateEntity = new CoordinateEntity();

        coordinateEntity.setPrimaryKeyCoordinate(new CoordinateEntity.PrimaryKeyCoordinate(riga,colonna,entitaEntityQuery.getId()));

        return mappaJSON.toString();
    }

    @Override
    @Transactional
    public String visualizzaLista(String email, String nome) throws SQLException {
        return entitaService.visualizzaListaEntitaInCartella(email,nome);
    }
}
