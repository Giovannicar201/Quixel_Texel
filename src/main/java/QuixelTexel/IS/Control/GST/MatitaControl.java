package QuixelTexel.IS.Control.GST;

import QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;
import QuixelTexel.IS.Exception.GEN.GEN.FolderNotFoundException;
import QuixelTexel.IS.Exception.GPA.GPL.PaletteNotFoundException;
import QuixelTexel.IS.Exception.GST.GSTException;
import QuixelTexel.IS.Exception.GST.InvalidColumnException;
import QuixelTexel.IS.Exception.GST.InvalidRowException;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapException;
import QuixelTexel.IS.Exception.Session.MissingSessionPixelArtException;
import QuixelTexel.IS.Service.GMP.GMP.MappaServiceImpl;
import QuixelTexel.IS.Service.GPA.GPA.PixelArtServiceImpl;
import QuixelTexel.IS.Service.GST.MatitaServiceImpl;
import QuixelTexel.IS.Utility.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class MatitaControl {

    @Autowired
    private MappaServiceImpl mappaService;
    @Autowired
    private PixelArtServiceImpl pixelArtService;

    /**
     * Gestisce la richiesta di piazzare un elemento sulla mappa o sulla pixel art.
     * L'elemento può essere un'entità sulla mappa o un colore sulla pixel art.
     *
     * @param elemento Stringa JSON che rappresenta l'elemento da piazzare, contenente nome dell'entità/colore, riga e colonna.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GSTException Eccezione generica del gestore degli strumenti (GST).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GSTException
     */
    @RequestMapping(value = "/matita/piazza", method = RequestMethod.POST)
    public void piazza(@RequestBody String elemento, HttpServletRequest request, HttpServletResponse response) throws GSTException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);

            JSONObject elementoJSON = (JSONObject) parser.parse(elemento);

            String nome = (String) elementoJSON.get("nome");
            String colore = (String) elementoJSON.get("colore");
            String riga = (String) elementoJSON.get("riga");
            String colonna = (String) elementoJSON.get("colonna");

            if(nome != null) {

                String mappa = SessionManager.getMappa(request);

                MatitaServiceImpl matitaService = new MatitaServiceImpl(mappaService);

                String mappaModificata = matitaService.usa(mappa,nome,riga,colonna,email);

                SessionManager.setMappa(request,mappaModificata);

            } else if(colore != null) {

                String pixelArt = SessionManager.getPixelArt(request);

                MatitaServiceImpl matitaService = new MatitaServiceImpl(pixelArtService);

                String pixelArtModificata = matitaService.usa(pixelArt,colore,riga,colonna,email);

                SessionManager.setPixelArt(request,pixelArtModificata);
            }

        } catch (ParseException | SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - PARSEEXCEPTION || SQLEXCEPTION.");
            }

        } catch (MissingSessionMapException e) {

            try {
                response.sendError(302, "MSME");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUNA MAPPA IN SESSIONE.");
            }

        } catch (MissingSessionPixelArtException e) {

            try {
                response.sendError(302, "MSPAE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUNA PIXEL ART IN SESSIONE.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (InvalidColumnException e) {

            try {
                response.sendError(500, "ICE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - COLONNA NON VALIDA.");
            }

        } catch (EntityNotFoundException e) {

            try {
                response.sendError(500, "ENFE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - ENTITA NON ESISTENTE.");
            }

        } catch (InvalidRowException e) {

            try {
                response.sendError(500, "IRE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - RIGA NON VALIDA.");
            }

        }
    }

    /**
     * Gestisce la richiesta di visualizzare la collezione di elementi contenuti in una cartella o in una palette.
     * L'elemento può essere un'entità sulla mappa o un colore sulla pixel art.
     *
     * @param elemento Stringa JSON che rappresenta l'elemento da visualizzare, contenente il tipo (mappa/pixelArt) e il nome di una cartella o di una palette.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @return Stringa JSON che rappresenta la collezione di elementi richiesta.
     * @throws GSTException Eccezione generica del gestore degli strumenti (GST).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GSTException
     */
    @RequestMapping(value = "/matita/visualizzaCollezioneElementi", method = RequestMethod.POST)
    @ResponseBody
    public String visualizzaCollezioneElementi(@RequestBody String elemento, HttpServletRequest request, HttpServletResponse response) throws GSTException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);

            JSONObject elementoJSON = (JSONObject) parser.parse(elemento);

            String semaforo = (String) elementoJSON.get("semaforo");
            String nome = (String) elementoJSON.get("nome");

            if (semaforo.equalsIgnoreCase("mappa")) {

                return mappaService.visualizzaCollezioneElementi(email,nome);

            } else if (semaforo.equalsIgnoreCase("pixelArt")) {

                return pixelArtService.visualizzaCollezioneElementi(email,nome);

            }

        } catch (ParseException | SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - PARSEEXCEPTION || SQLEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (FolderNotFoundException e) {

            try {
                response.sendError(500, "FNFE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - CARTELLA NON ESISTENTE.");
            }

        } catch (PaletteNotFoundException e) {

            try {
                response.sendError(500, "PNFE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - PALETTE NON ESISTENTE.");
            }

        }

        return null;
    }
}
