package QuixelTexel.IS.Control.GEV;

import QuixelTexel.IS.Entity.GEV.EventoEntity;
import QuixelTexel.IS.Exception.GEV.*;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapException;
import QuixelTexel.IS.Service.GEV.EventoService;
import QuixelTexel.IS.Service.GEV.IstruzioneService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EventoControl {

    @Autowired
    private EventoService eventoService;
    @Autowired
    private IstruzioneService istruzioneService;

    /**
     * Gestisce la richiesta di creazione di un nuovo evento all'interno di una mappa.
     *
     * @param evento Stringa JSON contenente le informazioni relative al nuovo evento.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GEVException Eccezione generica del gestore degli eventi (GEV).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GEVException
     */
    @RequestMapping(value = "/gestoreEventi/creaEvento", method = RequestMethod.POST)
    public void creaEvento(@RequestBody String evento, HttpServletRequest request, HttpServletResponse response) throws GEVException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);
            String mappa = SessionManager.getMappa(request);

            JSONObject eventoJSON = (JSONObject) parser.parse(evento);

            List<String> nomiIstruzioni = new ArrayList<>();
            List<String> valoriIstruzioni = new ArrayList<>();

            String nome = (String) eventoJSON.get("nome");
            String riga = (String) eventoJSON.get("riga");
            String colonna = (String) eventoJSON.get("colonna");

            JSONArray istruzioni = (JSONArray) eventoJSON.get("istruzioni");

            for(Object obj : istruzioni) {
                JSONObject istruzioneJSON = (JSONObject) obj;

                String nomeIstruzione = (String) istruzioneJSON.get("nome");
                String valoreIstruzione = (String) istruzioneJSON.get("valore");

                nomiIstruzioni.add(nomeIstruzione);
                valoriIstruzioni.add(valoreIstruzione);
            }

            eventoService.creaEvento(email,nome,riga,colonna,nomiIstruzioni,valoriIstruzioni);

        } catch (ParseException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - PARSEEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (MissingSessionMapException e) {

            try {
                response.sendError(302, "MSME");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - NESSUNA MAPPA IN SESSIONE.");
            }

        } catch (InvalidCycleValueException e) {

            try {
                response.sendError(500, "ICVE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - CICLO NON VALIDO.");
            }

        } catch (InvalidCyclesException e) {

            try {
                response.sendError(500, "ICE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - NUMERO DI ISTRUZIONI INIZIO CICLO E FINE CICLO NON VALIDO.");
            }

        } catch (InvalidEventNameException e) {

            try {
                response.sendError(500, "IENE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - NOME NON VALIDO.");
            }

        } catch (InvalidNestedCycleException e) {

            try {
                response.sendError(500, "INCE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - CICLO ANNIDATO NON VALIDO.");
            }

        } catch (InvalidTextValueException e) {

            try {
                response.sendError(500, "ITVE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE -TESTO NON VALIDO.");
            }

        } catch (InvalidPositionException e) {

            try {
                response.sendError(500, "IPE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - POSIZIONE NON VALIDA");
            }

        } catch (InvalidDialogValueException e) {

            try {
                response.sendError(500, "IDVE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - DIALOGO NON VALIDO.");
            }

        } catch (NotUniqueEventException e) {

            try {
                response.sendError(500, "NUEE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - EVENTO GIÀ ESISTENTE.");
            }

        } catch (InvalidNumberOfInstructionsException e) {

            try {
                response.sendError(500, "INIE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - NUMERO DI ISTRUZIONI NON VALIDO.");
            }

        }
    }

    /**
     * Gestisce la richiesta di visualizzazione dell'anteprima di un evento.
     *
     * @param nomeEvento Stringa JSON contenente il nome dell'evento di cui visualizzare l'anteprima.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @return Una stringa JSON contenente le informazioni sull'anteprima dell'evento.
     * @throws GEVException Eccezione generica del gestore degli eventi (GEV).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GEVException
     */
    @RequestMapping(value = "/gestoreEventi/visualizzaAnteprima", method = RequestMethod.POST)
    @ResponseBody
    public String visualizzaAnteprimaEvento(@RequestBody String nomeEvento,HttpServletRequest request,HttpServletResponse response) throws GEVException {

        String nomiIstruzioni = new JSONObject().toString();

        try {

            JSONParser parser = new JSONParser();

            JSONObject nomiJSON = (JSONObject) parser.parse(nomeEvento);

            String nome = (String) nomiJSON.get("nome");

            String email = SessionManager.getEmail(request);

            EventoEntity eventoEntity = eventoService.get(nome,email);

            nomiIstruzioni = istruzioneService.visualizzaListaIstruzioniInEvento(eventoEntity);

        } catch (ParseException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - PARSEEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }
        }

        response.setContentType("text/plain");

        return nomiIstruzioni;
    }

    /**
     * Gestisce la richiesta di visualizzazione della lista di eventi associati a un utente.
     *
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @return Una stringa JSON contenente la lista di eventi associati all'utente.
     * @throws GEVException Eccezione generica del gestore degli eventi (GEV).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GEVException
     */
    @RequestMapping(value = "/gestoreEventi/trovaEventi", method = RequestMethod.GET)
    @ResponseBody
    public String visualizzaListaEventi(HttpServletRequest request, HttpServletResponse response) throws GEVException {

        String evento = new JSONObject().toString();

        try {

            String email = SessionManager.getEmail(request);

            evento = eventoService.visualizzaListaEventi(email);

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GEVException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        }

        response.setContentType("text/plain");

        return evento;
    }
}
