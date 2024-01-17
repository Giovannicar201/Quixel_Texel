package QuixelTexel.IS.Control.GST;

import QuixelTexel.FIA.Service.IAServiceAdapter;
import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Exception.GST.GSTException;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapSelectionException;
import QuixelTexel.IS.Service.GEN.GEN.EntitaService;
import QuixelTexel.IS.Service.GST.MatitaServiceImpl;
import QuixelTexel.IS.Utility.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Controller
public class IAControl {

    @Autowired
    protected MatitaServiceImpl matitaService;
    @Autowired
    protected EntitaService entitaService;

    /**
     * Gestisce la richiesta di generazione automatica di un individuo sulla mappa.
     * L'individuo generato contiene entità posizionate in modo casuale tra i punti selezionati sulla mappa.
     *
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GSTException Eccezione generica del gestore degli strumenti (GST).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GSTException
     */
    @RequestMapping(value = "/IA/genera", method = RequestMethod.POST)
    public void genera(HttpServletRequest request, HttpServletResponse response) throws GSTException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);

            String mappa = SessionManager.getMappa(request);

            JSONObject mappaJSON = (JSONObject) parser.parse(mappa);
            JSONArray entita = (JSONArray) mappaJSON.get("mappa");

            JSONObject selezioneJSON = (JSONObject) parser.parse(SessionManager.getSelezioneMappa(request));

            String rigaPrimoPunto = (String) selezioneJSON.get("primaRiga");
            String colonnaPrimoPunto = (String) selezioneJSON.get("primaColonna");
            String rigaSecondoPunto = (String) selezioneJSON.get("secondaRiga");
            String colonnaSecondoPunto = (String) selezioneJSON.get("secondaColonna");

            IAServiceAdapter iaServiceAdapter = new IAServiceAdapter();
            String individuo = iaServiceAdapter.genera(mappa,rigaPrimoPunto,colonnaPrimoPunto,rigaSecondoPunto,colonnaSecondoPunto);

            JSONObject individuoJSON = (JSONObject) parser.parse(individuo);
            JSONArray entitaIndividuo = (JSONArray) individuoJSON.get("entita");


            for(Object obj : entitaIndividuo) {
                JSONObject entitaIndividuoJSON = (JSONObject) obj;

                int id = Math.toIntExact((Long) entitaIndividuoJSON.get("id"));

                String nome = entitaService.get(id).getNome();
                String riga = (String) entitaIndividuoJSON.get("riga");
                String colonna = (String) entitaIndividuoJSON.get("colonna");

                EntitaEntity entitaEntityQuery = entitaService.get(nome,email);

                for (Object entitaOBJ : entita) {
                    JSONObject entitaJSON = (JSONObject) entitaOBJ;

                    if (((String) entitaJSON.get("riga")).compareTo(riga) == 0 &&
                            ((String) entitaJSON.get("colonna")).compareTo(colonna) == 0) {

                        entitaJSON.put("id", entitaEntityQuery.getId());

                        Blob immagine = entitaEntityQuery.getImmagineEntity().getImmagine();
                        byte[] bytes = immagine.getBytes(1, (int) immagine.length());

                        entitaJSON.put("immagine", Base64.getEncoder().encodeToString(bytes));
                    }
                }
            }

            mappaJSON.put("mappa",entita);

            SessionManager.setMappa(request,mappaJSON.toString());

        } catch (ParseException | SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - PARSEEXCEPTION || SQLEXCEPTION.");
            }

        } catch (MissingSessionMapSelectionException e) {

            try {
                response.sendError(302, "MSMSE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUNA SELEZIONE MAPPA IN SESSIONE.");
            }

        } catch (MissingSessionMapException e) {

            try {
                response.sendError(302, "MSME");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUNA MAPPA IN SESSIONE.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GSTException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        }
    }
}
