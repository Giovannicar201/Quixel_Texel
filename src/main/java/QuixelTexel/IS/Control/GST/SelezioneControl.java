package QuixelTexel.IS.Control.GST;

import QuixelTexel.IS.Exception.GST.GSTException;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Utility.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;

@Controller
public class SelezioneControl {

    /**
     * Gestisce la richiesta di definizione di un'area di selezione sulla mappa.
     *
     * @param selezione Stringa JSON contenente le coordinate dell'area di selezione.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GSTException Eccezione generica del gestore degli strumenti (GST).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GSTException
     */
    @RequestMapping(value = "/selezione/selezioneAreaMappa", method = RequestMethod.POST)
    public void selezioneAreaMappa(@RequestBody String selezione, HttpServletRequest request, HttpServletResponse response) throws GSTException {

        try {

            SessionManager.getEmail(request);

            SessionManager.setSelezioneMappa(request, selezione);

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        }
    }
}
