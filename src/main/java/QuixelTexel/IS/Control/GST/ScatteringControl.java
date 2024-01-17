package QuixelTexel.IS.Control.GST;

import QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;
import QuixelTexel.IS.Exception.GST.*;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapSelectionException;
import QuixelTexel.IS.Service.GST.ScatteringService;
import QuixelTexel.IS.Utility.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ScatteringControl {

    @Autowired
    private ScatteringService scatteringService;

    /**
     * Gestisce la richiesta di dispersione di entità sulla mappa.
     * Le entità vengono disperse all'interno di una selezione sulla mappa,
     * rispettando le proprietà specificate (nome della cartella, priorità, percentuale di riempimento).
     *
     * @param scatter Stringa JSON che rappresenta i dati per la dispersione, contenente il nome della cartella selezionata, le proprietà e la percentuale di riempimento.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GSTException Eccezione generica del gestore degli strumenti (GST).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GSTException
     */
    @RequestMapping(value = "/scattering/scatter", method = RequestMethod.POST)
    public void scatter(@RequestBody String scatter, HttpServletRequest request, HttpServletResponse response) throws GSTException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);
            String mappa = SessionManager.getMappa(request);
            String selezione = SessionManager.getSelezioneMappa(request);

            List<String> nomiEntita = new ArrayList<>();
            List<String> prioritaEntita = new ArrayList<>();

            JSONObject selezioneJSON = (JSONObject) parser.parse(selezione);
            JSONObject scatterJSON = (JSONObject) parser.parse(scatter);

            JSONArray proprieta = (JSONArray) scatterJSON.get("proprieta");

            String rigaPrimoPunto = (String) selezioneJSON.get("primaRiga");
            String colonnaPrimoPunto = (String) selezioneJSON.get("primaColonna");
            String rigaSecondoPunto = (String) selezioneJSON.get("secondaRiga");
            String colonnaSecondoPunto = (String) selezioneJSON.get("secondaColonna");

            String percentualeRiempimento = (String) scatterJSON.get("percentualeRiempimento");

            for(Object obj : proprieta) {

                JSONObject proprietaJSON = (JSONObject) obj;

                String nome = (String) proprietaJSON.get("nome");
                String priorita = (String) proprietaJSON.get("priorita");

                nomiEntita.add(nome);
                prioritaEntita.add(priorita);

            }

            String mappaModificata = scatteringService.scatter(mappa,rigaPrimoPunto,colonnaPrimoPunto,rigaSecondoPunto,colonnaSecondoPunto,percentualeRiempimento,nomiEntita,prioritaEntita,email);

            SessionManager.setMappa(request,mappaModificata);

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

        } catch (MissingSessionMapException e) {

            try {
                response.sendError(302, "MSME");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUNA MAPPA IN SESSIONE.");
            }

        } catch (MissingSessionMapSelectionException e) {

            try {
                response.sendError(302, "MSMSE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUNA SELEZIONE MAPPA IN SESSIONE.");
            }

        } catch (EntityNotFoundException e) {

            try {
                response.sendError(500, "MSMSE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUNA SELEZIONE MAPPA IN SESSIONE.");
            }

        } catch (InvalidFillPercentageException e) {

            try {
                response.sendError(500, "IFPE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - PERCENTUALE DI RIEMPIMENTO NON VALIDA.");
            }

        } catch (InvalidNumberOfPriorityException e) {

            try {
                response.sendError(500, "INPE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NUMERO DI PRIORITÀ NON VALIDO.");
            }

        } catch (InvalidPriorityPercentageSumException e) {

            try {
                response.sendError(500, "IPPSE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - SOMMA DELLE PRIORITÀ NON VALIDA.");
            }

        } catch (InvalidColumnException e) {

            try {
                response.sendError(500, "ICE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - COLONNA NON VALIDA.");
            }

        } catch (InvalidRowException e) {

            try {
                response.sendError(500, "IRE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - RIGA NON VALIDA.");
            }

        }
    }
}
