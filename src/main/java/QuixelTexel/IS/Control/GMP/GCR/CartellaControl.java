package QuixelTexel.IS.Control.GMP.GCR;

import QuixelTexel.IS.Exception.GMP.GCR.*;
import QuixelTexel.IS.Exception.GMP.GCR.GCRException;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Service.GMP.GCR.CartellaService;
import QuixelTexel.IS.Utility.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;

@Controller
public class CartellaControl {

    @Autowired
    private CartellaService cartellaService;

    /**
     * Gestisce la richiesta di creazione di una nuova cartella.
     *
     * @param nomeCartella Nome della nuova cartella da creare.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GCRException Eccezione generica del gestore delle cartelle (GCR).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GCRException
     */
    @RequestMapping(value = "/gestoreCartelle/creaCartella", method = RequestMethod.POST)
    public void creaCartella(@RequestBody String nomeCartella, HttpServletRequest request, HttpServletResponse response) throws GCRException {

        try {

            String email = SessionManager.getEmail(request);

            cartellaService.creaCartella(nomeCartella, email);

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GCRException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (InvalidFolderNameException e) {

            try {
                response.sendError(500, "IFNE");
            } catch (IOException ex) {
                throw new GCRException("ERRORE - NOME CARTELLA NON VALIDO.");
            }

        } catch (NotUniqueFolderException e) {

            try {
                response.sendError(500, "NUFE");
            } catch (IOException ex) {
                throw new GCRException("ERRORE - CARTELLA GIÀ ESISTENTE.");
            }

        }
    }

    /**
     * Gestisce la richiesta di visualizzazione della lista di cartelle associate a un utente.
     *
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @return Una stringa JSON contenente la lista di nomi delle cartelle associate all'utente.
     * @throws GCRException Eccezione generica del gestore delle cartelle (GCR).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GCRException
     */
    @RequestMapping(value = "/gestoreCartelle/visualizzaListaCartelle", method = RequestMethod.GET)
    @ResponseBody
    public String visualizzaListaCartelle(HttpServletRequest request, HttpServletResponse response) throws GCRException {

        String nomiCartelle = new JSONObject().toString();

        try {

            String email = SessionManager.getEmail(request);

            nomiCartelle = cartellaService.visualizzaListaCartelle(email);

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GCRException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }
        }

        response.setContentType("text/plain");

        return nomiCartelle;
    }
}
