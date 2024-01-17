package QuixelTexel.IS.Control.GEN.GEN;

import QuixelTexel.IS.Exception.GEN.GEN.*;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Exception.Session.MissingSessionMapException;
import QuixelTexel.IS.Service.GEN.GEN.EntitaService;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EntitaControl {

    @Autowired
    private EntitaService entitaService;

    /**
     * Gestisce la richiesta di creazione di un'entità all'interno del sistema.
     *
     * @param entita Stringa JSON contenente le informazioni sull'entità da creare.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GENException Eccezione generica del gestore delle entità (GEN).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GENException
     */
    @RequestMapping(value = "/gestoreEntita/creaEntità", method = RequestMethod.POST)
    public void creaEntita(@RequestBody String entita, HttpServletRequest request, HttpServletResponse response)
            throws GENException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);

            JSONObject entitaJSON = (JSONObject) parser.parse(entita);

            JSONArray proprieta = (JSONArray) entitaJSON.get("proprieta");

            List<String> nomiProprieta = new ArrayList<>();
            List<String> valoriProprieta = new ArrayList<>();

            String nomeImmagine = (String) entitaJSON.get("nomeImmagine");
            String nome = (String) entitaJSON.get("nome");
            String collisioni = (String) entitaJSON.get("collisioni");
            String nomeCartella = (String) entitaJSON.get("nomeCartella");

            for(Object obj : proprieta) {

                JSONObject proprietaJSON = (JSONObject) obj;

                String nomeProprieta = (String) proprietaJSON.get("nomeProprieta");
                String valoreProprieta = (String) proprietaJSON.get("valoreProprieta");

                nomiProprieta.add(nomeProprieta);
                valoriProprieta.add(valoreProprieta);

            }

            entitaService.creaEntita(email,nomeImmagine,nome,collisioni,nomeCartella,nomiProprieta,valoriProprieta);

        } catch (ParseException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - PARSEEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (FolderNotFoundException e) {

            try {
                response.sendError(500, "MSEE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - CARTELLA NON ESISTENTE.");
            }

        } catch (InvalidEntityNameException e) {

            try {
                response.sendError(500, "IENE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - NOME NON VALIDO.");
            }

        } catch (InvalidNumberOfPropertyException e) {

            try {
                response.sendError(500, "INPE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - NUMERO DI PROPRIETÀ NON VALIDO.");
            }

        } catch (NotUniqueEntityException e) {

            try {
                response.sendError(500, "NUEE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - ENTITÀ GIÀ ESISTENTE.");
            }

        } catch (ImageNotFoundException e) {

            try {
                response.sendError(500, "INFE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - IMMAGINE NON ESISTENTE.");
            }

        } catch (InvalidCollisionException e) {

            try {
                response.sendError(500, "ICE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - COLLISIONI NON VALIDE.");
            }

        } catch (ImageAlreadyUsedException e) {

            try {
                response.sendError(500, "IAUE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - IMMAGINE GIÀ USATA.");
            }

        } catch (InvalidPropertyNameException e) {

            try {
                response.sendError(500, "IPNE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - NOME PROPRIETÀ NON VALIDO.");
            }

        } catch (NotUniquePropertyException e) {

            try {
                response.sendError(500, "NUPE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - PROPRIETÀ GIÀ ESISTENTE.");
            }

        } catch (InvalidPropertyValueException e) {

            try {
                response.sendError(500, "IPVE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - VALORE PROPRIETÀ NON VALIDO.");
            }

        }
    }

    /**
     * Gestisce la richiesta di eliminazione di un'entità all'interno del sistema.
     *
     * @param nome Nome dell'entità da eliminare.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GENException Eccezione generica del gestore delle entità (GEN).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GENException
     */
    @RequestMapping(value = "/gestoreEntita/eliminaEntità", method = RequestMethod.POST)
    public void eliminaEntita(@RequestBody String nome, HttpServletRequest request, HttpServletResponse response ) throws GENException {

        try {

            String email = SessionManager.getEmail(request);
            String mappa = SessionManager.getMappa(request);

            String mappaModificata = entitaService.eliminaEntita(mappa,nome,email);

            SessionManager.setMappa(request,mappaModificata);

        } catch (ParseException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - PARSEEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (MissingSessionMapException e) {

            try {
                response.sendError(302, "MSME");
            } catch (IOException ex) {
                throw new GENException("ERRORE - NESSUNA MAPPA IN SESSIONE.");
            }

        } catch (EntityNotFoundException e) {

            try {
                response.sendError(500, "ENFE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - ENTITÀ NON ESISTENTE.");
            }

        }
    }

    /**
     * Gestisce la richiesta di visualizzazione della lista di entità create da un utente.
     *
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @return Una stringa JSON che rappresenta la lista di entità dell'utente.
     * @throws GENException Eccezione generica del gestore delle entità (GEN).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GENException
     */
    @RequestMapping(value = "/gestoreEntita/visualizzaListaEntità", method = RequestMethod.POST)
    @ResponseBody
    public String visualizzaListaEntita(HttpServletRequest request, HttpServletResponse response ) throws GENException {

        String immagini = new JSONObject().toString();

        try {

            immagini = entitaService.visualizzaListaEntita(SessionManager.getEmail(request));

        }catch (SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - SQLEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }
        }

        response.setContentType("text/plain");

        return immagini;
    }

    /**
     * Gestisce la richiesta di visualizzazione di un'entità specifica creata da un utente.
     *
     * @param nome Una stringa rappresentante il nome dell'entità da visualizzare.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @return Una stringa JSON che rappresenta i dettagli dell'entità richiesta.
     * @throws GENException Eccezione generica del gestore delle entità (GEN).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GENException
     */
    @RequestMapping(value = "/gestoreEntita/visualizzaEntità", method = RequestMethod.POST)
    @ResponseBody
    public String visualizzaEntita(@RequestBody String nome, HttpServletRequest request, HttpServletResponse response ) throws GENException {

        String entita = new JSONObject().toString();

        try {

            String email = SessionManager.getEmail(request);

            entita = entitaService.visualizzaEntita(nome,email);

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (EntityNotFoundException e) {

            try {
                response.sendError(500, "ENFE");
            } catch (IOException ex) {
                throw new GENException("ERRORE - ENTITÀ NON ESISTENTE.");
            }

        }

        response.setContentType("text/plain");

        return entita;
    }
}

