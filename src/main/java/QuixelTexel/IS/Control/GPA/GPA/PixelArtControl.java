package QuixelTexel.IS.Control.GPA.GPA;

import QuixelTexel.IS.Exception.GEN.GIM.NotUniqueImageException;
import QuixelTexel.IS.Exception.GPA.GPA.GPAException;
import QuixelTexel.IS.Exception.GPA.GPA.InvalidPixelArtNameException;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Exception.Session.MissingSessionPixelArtException;
import QuixelTexel.IS.Service.GPA.GPA.PixelArtServiceImpl;
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
public class PixelArtControl {

    @Autowired
    private PixelArtServiceImpl pixelArtService;

    /**
     * Gestisce la richiesta di creazione di una nuova pixel art temporanea.
     *
     * @param pixelArt Stringa JSON contenente il nome, l'altezza e la larghezza della pixel art.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GPAException Eccezione generica del gestore della pixel art (GPA).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GPAException
     */
    @RequestMapping(value = "/gestorePixelArt/creaPixelArt", method = RequestMethod.POST)
    public void creaPixelArt(@RequestBody String pixelArt, HttpServletRequest request, HttpServletResponse response) throws GPAException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);

            JSONObject pixelArtJSON = (JSONObject) parser.parse(pixelArt);

            String nome = (String) pixelArtJSON.get("nome");
            String altezza = (String) pixelArtJSON.get("altezza");
            String larghezza = (String) pixelArtJSON.get("larghezza");

            String pixelArtVuota = pixelArtService.crea(email,nome,altezza,larghezza);

            SessionManager.setPixelArt(request,pixelArtVuota);

        } catch (ParseException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GPAException("ERRORE - PARSEEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GPAException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (InvalidPixelArtNameException e) {

            try {
                response.sendError(500, "IPANE");
            } catch (IOException ex) {
                throw new GPAException("ERRORE - NOME NON VALIDO.");
            }

        }
    }

    /**
     * Gestisce la richiesta d'integrazione della pixel art attualmente esistente all'interno della lista delle immagini dell'utente.
     *
     * @param immagine Stringa JSON contenente il nome della pixel art attualmente esistente.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GPAException Eccezione generica del gestore della pixel art (GPA).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GPAException
     */
    @RequestMapping(value = "/gestorePixelArt/integraImmagine", method = RequestMethod.POST)
    public void integraPixelArt(@RequestBody String immagine, HttpServletRequest request, HttpServletResponse response) throws GPAException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);

            JSONObject pixelArt = (JSONObject) parser.parse(SessionManager.getPixelArt(request));

            String nome = (String) pixelArt.get("nome");

            pixelArtService.integraPixelArt(immagine,nome,email);


        } catch (ParseException | SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GPAException("ERRORE - PARSEEEXCEPTION || SQLEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GPAException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (MissingSessionPixelArtException e) {

            try {
                response.sendError(302, "MSPAE");
            } catch (IOException ex) {
                throw new GPAException("ERRORE - NESSUNA PIXEL ART IN SESSIONE.");
            }

        } catch (NotUniqueImageException e) {

            try {
                response.sendError(500, "NUIE");
            } catch (IOException ex) {
                throw new GPAException("ERRORE - IMMAGINE GIÀ ESISTENTE.");
            }

        }
    }

    /**
     * Gestisce la richiesta di recupero di una pixel art.
     *
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @return Stringa JSON rappresentante la pixel art.
     * @throws GPAException Eccezione generica del gestore della pixel art (GPA).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GPAException
     */
    @RequestMapping(value = "/gestorePixelArt/recuperaPixelArt", method = RequestMethod.POST)
    @ResponseBody
    public String caricaPixelArt(HttpServletRequest request, HttpServletResponse response) throws GPAException {

        String pixelArt = new JSONObject().toString();

        try {

            SessionManager.getEmail(request);

            pixelArt = SessionManager.getPixelArt(request);

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GPAException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (MissingSessionPixelArtException e) {

            try {
                response.sendError(302, "MSPAE");
            } catch (IOException ex) {
                throw new GPAException("ERRORE - NESSUNA PIXEL ART IN SESSIONE.");
            }

        }

        response.setContentType("text/plain");

        return pixelArt;
    }
}

