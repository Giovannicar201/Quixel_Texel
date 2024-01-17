package QuixelTexel.IS.Control.GST;

import QuixelTexel.IS.Exception.GST.GSTException;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapException;
import QuixelTexel.IS.Exception.Session.MissingSessionPixelArtException;
import QuixelTexel.IS.Service.GMP.GMP.MappaServiceImpl;
import QuixelTexel.IS.Service.GPA.GPA.PixelArtServiceImpl;
import QuixelTexel.IS.Service.GST.GommaService;
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
import java.io.IOException;

@Controller
public class GommaControl {

    @Autowired
    private MappaServiceImpl mappaService;
    @Autowired
    private PixelArtServiceImpl pixelArtService;

    /**
     * Gestisce la richiesta di rimozione di un elemento (pixel o entità) dalla mappa o dalla pixel art.
     *
     * @param elemento Stringa JSON rappresentante l'elemento da rimuovere e le coordinate (riga, colonna).
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GSTException Eccezione generica del gestore degli strumenti (GST).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GSTException
     */
    @RequestMapping(value = "/gomma/rimuovi", method = RequestMethod.POST)
    public void rimuovi(@RequestBody String elemento, HttpServletRequest request, HttpServletResponse response) throws GSTException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);

            JSONObject elementoJSON = (JSONObject) parser.parse(elemento);

            String semaforo = (String) elementoJSON.get("semaforo");
            String riga = (String) elementoJSON.get("riga");
            String colonna = (String) elementoJSON.get("colonna");

            if (semaforo.equalsIgnoreCase("mappa")) {

                String mappa = SessionManager.getMappa(request);

                GommaService gommaService = new GommaService(mappaService);

                String mappaModificata = gommaService.usa(mappa, null, riga, colonna, email);

                SessionManager.setMappa(request, mappaModificata);

            } else if (semaforo.equalsIgnoreCase("pixelArt")) {

                String pixelArt = SessionManager.getPixelArt(request);

                GommaService gommaService = new GommaService(pixelArtService);

                String pixelArtModificata = gommaService.usa(pixelArt, null, riga, colonna, email);

                SessionManager.setPixelArt(request, pixelArtModificata);
            }

        } catch (ParseException e ) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - PARSEEXCEPTION || SQLEXCEPTION.");
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

        } catch (MissingSessionMapException e) {

            try {
                response.sendError(302, "MSME");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUNA MAPPA IN SESSIONE.");
            }

        }
    }
}
