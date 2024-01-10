package QuixelTexel.IS.Control.GMP.GST;

import QuixelTexel.FIA.Service.IAServiceAdapter;
import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Exception.GMP.GMP.GMPException;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapSelectionException;
import QuixelTexel.IS.Service.GEN.GEN.EntitaService;
import QuixelTexel.IS.Service.GMP.GST.MatitaService;
import QuixelTexel.IS.Utility.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

@Controller
public class IAControl {

    @Qualifier("matitaServiceMappaImpl")
    @Autowired
    protected MatitaService matitaService;
    @Autowired
    protected EntitaService entitaService;

    @RequestMapping(value = "/IA/genera", method = RequestMethod.POST)

    public void genera(HttpServletRequest request, HttpServletResponse response) throws GMPException {

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

            System.out.println(SessionManager.getMappa(request));

        } catch (ParseException | SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GMPException("ERRORE - PARSEEXCEPTION || SQLEXCEPTION.");
            }

        } catch (MissingSessionMapSelectionException e) {

            try {
                response.sendError(302, "MSMSE");
            } catch (IOException ex) {
                throw new GMPException("ERRORE - NESSUNA SELEZIONE MAPPA IN SESSIONE.");
            }

        } catch (MissingSessionMapException e) {

            try {
                response.sendError(302, "MSME");
            } catch (IOException ex) {
                throw new GMPException("ERRORE - NESSUNA MAPPA IN SESSIONE.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GMPException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        }

    }

}

