package QuixelTexel.IS.Control.GEN.GIM;

import QuixelTexel.IS.Exception.GEN.GIM.GIMException;
import QuixelTexel.IS.Exception.GEN.GIM.InvalidFileSizeException;
import QuixelTexel.IS.Exception.GEN.GIM.NotUniqueImageException;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Service.GEN.GIM.ImmagineService;
import QuixelTexel.IS.Utility.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class ImmagineControl {
    
    @Autowired
    private ImmagineService immagineService;

    /**
     * Gestisce la richiesta di caricamento di un'immagine da parte dell'utente autenticato.
     *
     * @param immagine Oggetto MultipartFile che rappresenta il file dell'immagine da caricare.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GIMException Eccezione generica del gestore delle immagini (GIM).
     * @see MultipartFile
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GIMException
     */
    @RequestMapping(value = "/gestoreImmagini/caricaImmagine", method = RequestMethod.POST)
    public void caricaImmagine(@RequestPart("file") MultipartFile immagine, HttpServletRequest request, HttpServletResponse response)
            throws GIMException {

        try {

            String email = SessionManager.getEmail(request);

            immagineService.caricaImmagine(immagine, email);

        } catch (IOException | SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - IOEXCEPTION || SQLEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (NotUniqueImageException e) {

            try {
                response.sendError(500, "NUIE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - IMMAGINE GIÀ ESISTENTE.");
            }

        } catch (InvalidFileSizeException e) {

            try {
                response.sendError(500, "IFSE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - DIMENSIONE DELL'IMMAGINE NON VALIDA.");
            }
        }
    }

    /**
     * Gestisce la richiesta di visualizzazione della lista delle immagini associate all'utente autenticato.
     *
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @return Una stringa JSON contenente le informazioni sulla lista delle immagini dell'utente.
     * @throws GIMException Eccezione generica del gestore delle immagini (GIM).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GIMException
     */
    @RequestMapping(value = "/gestoreImmagini/visualizzaListaImmagini", method = RequestMethod.GET)
    @ResponseBody
    public String visualizzaListaImmagini(HttpServletRequest request, HttpServletResponse response) throws GIMException {

        String immagini = new JSONObject().toString();

        try {

            String email = SessionManager.getEmail(request);

            immagini = immagineService.visualizzaListaImmagini(email);

        }catch (SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - SQLEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }
        }

        response.setContentType("text/plain");

        return immagini;
    }
}
