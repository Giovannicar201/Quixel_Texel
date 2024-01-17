package QuixelTexel.IS.Service.GPA.GPA;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEN.GIM.ImmagineEntity;
import QuixelTexel.IS.Entity.GPA.PaletteEntity;
import QuixelTexel.IS.Exception.GEN.GIM.NotUniqueImageException;
import QuixelTexel.IS.Exception.GPA.GPA.InvalidPixelArtNameException;
import QuixelTexel.IS.Exception.GPA.GPL.PaletteNotFoundException;
import QuixelTexel.IS.Exception.GST.InvalidColumnException;
import QuixelTexel.IS.Exception.GST.InvalidRowException;
import QuixelTexel.IS.Service.GAC.UtenteService;
import QuixelTexel.IS.Service.GEN.GIM.ImmagineService;
import QuixelTexel.IS.Service.GPA.GPL.ColoreService;
import QuixelTexel.IS.Service.GPA.GPL.PaletteService;
import QuixelTexel.IS.Utility.Validator;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialBlob;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Service
public class PixelArtServiceImpl implements PixelArtService {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ColoreService coloreService;
    @Autowired
    private PaletteService paletteService;
    @Autowired
    private ImmagineService immagineService;

    @Override
    @Transactional
    public String crea(String email, String nome, String altezza, String larghezza) throws InvalidPixelArtNameException {

        if(!Validator.isPixelArtNameValid(nome))
            throw new InvalidPixelArtNameException("ERRORE - NOME NON VALIDO.");

        JSONObject pixelArtJSON = new JSONObject();
        JSONArray colori = new JSONArray();

        for(int riga = 0; riga < 32; riga++) {
            for (int colonna = 0; colonna < 32; colonna++) {

                JSONObject coloreJSON = new JSONObject();

                coloreJSON.put("riga",riga + "");
                coloreJSON.put("colonna",colonna + "");
                coloreJSON.put("colore","");

                colori.add(coloreJSON);
            }
        }

        pixelArtJSON.put("pixelArt",colori);
        pixelArtJSON.put("nome", nome);

        return pixelArtJSON.toString();
    }

    @Override
    @Transactional
    public String piazza(String pixelArt, String colore, String riga, String colonna, String email)
            throws InvalidRowException,
            InvalidColumnException,
            ParseException {

        JSONParser parser = new JSONParser();

        if(!Validator.isRowValid(riga,32))
            throw new InvalidRowException("ERRORE - RIGA NON VALIDA.");

        if(!Validator.isColumnValid(colonna,32))
            throw new InvalidColumnException("ERRORE - COLONNA NON VALIDA.");

        JSONObject pixelArtJSON = (JSONObject) parser.parse(pixelArt);

        JSONArray colori = (JSONArray) pixelArtJSON.get("pixelArt");

        for (Object entitaOBJ : colori) {
            JSONObject coloreJSON = (JSONObject) entitaOBJ;

            if(((String)coloreJSON.get("riga")).compareTo(riga) == 0 && ((String)coloreJSON.get("colonna")).compareTo(colonna) == 0)
                coloreJSON.put("colore",colore);
        }

        pixelArtJSON.put("pixelArt",colori);

        return pixelArtJSON.toString();
    }

    @Override
    @Transactional
    public String rimuovi(String pixelArt, String riga, String colonna, String email)
            throws InvalidRowException,
            InvalidColumnException,
            ParseException {

        JSONParser parser = new JSONParser();

        if(!Validator.isRowValid(riga,32))
            throw new InvalidRowException("ERRORE - RIGA NON VALIDA.");

        if(!Validator.isColumnValid(colonna,32))
            throw new InvalidColumnException("ERRORE - COLONNA NON VALIDA.");

        JSONObject pixelArtJSON = (JSONObject) parser.parse(pixelArt);

        JSONArray colori = (JSONArray) pixelArtJSON.get("pixelArt");

        for (Object obj : colori) {
            JSONObject coloreJSON = (JSONObject) obj;

            if(((String)coloreJSON.get("riga")).compareTo(riga) == 0 && ((String)coloreJSON.get("colonna")).compareTo(colonna) == 0)
                coloreJSON.put("colore","");
        }

        pixelArtJSON.put("pixelArt",colori);

        return pixelArtJSON.toString();
    }

    @Override
    @Transactional
    public String visualizzaCollezioneElementi(String email, String nome)
            throws SQLException,
            PaletteNotFoundException {

        PaletteEntity paletteEntityQuery = paletteService.get(nome,email);

        if(paletteEntityQuery == null)
            throw new PaletteNotFoundException("ERRORE - PALETTE NON ESISTENTE.");

        return coloreService.visualizzaListaColoriInPalette(paletteEntityQuery);
    }

    @Override
    @Transactional
    public void integraPixelArt(String immagine, String nome, String email)
            throws SQLException,
            NotUniqueImageException,
            ParseException {

        JSONParser parser = new JSONParser();

        UtenteEntity utenteEntity = utenteService.get(email);

        ImmagineEntity immagineEntity = new ImmagineEntity();

        ImmagineEntity immagineEntityQuery = immagineService.get(nome, email);

        JSONObject immagineJSON = (JSONObject) parser.parse(immagine);

        Blob immagineBlob = new SerialBlob(Base64.getDecoder().decode(((String) immagineJSON.get("immagine")).getBytes(StandardCharsets.UTF_8)));

        if(immagineEntityQuery != null)
            throw new NotUniqueImageException("ERRORE - IMMAGINE GIÀ ESISTENTE.");

        immagineEntity.setImmagine(immagineBlob);
        immagineEntity.setNome(nome);
        immagineEntity.setUtenteEntity(utenteEntity);

        immagineService.save(immagineEntity);
    }
}

