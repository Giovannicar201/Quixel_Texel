package QuixelTexel.IS.Control.GAC;

import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Exception.GAC.*;
import QuixelTexel.IS.Service.GAC.UtenteService;
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
import java.security.NoSuchAlgorithmException;

@Controller
public class UtenteControl {

    @Autowired
    private UtenteService utenteService;

    /**
     * Gestisce la richiesta di signup di un nuovo utente.
     *
     * @param signup Stringa JSON contenente le informazioni relative all'utente da registrare.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GACException Eccezione generica del gestore degli accessi (GAC).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GACException
     */
    @RequestMapping(value = "/gestoreAccessi/signup", method = RequestMethod.POST)
    public void signup(@RequestBody String signup, HttpServletRequest request, HttpServletResponse response) throws GACException {

        JSONParser parser = new JSONParser();

        try {

            JSONObject signupJSON = (JSONObject) parser.parse(signup);

            String email = (String) signupJSON.get("email");
            String nome = (String) signupJSON.get("nome");
            String password = (String) signupJSON.get("password");
            String passwordRipetuta = (String) signupJSON.get("passwordRipetuta");

            utenteService.signup(email,nome,password,passwordRipetuta);

            SessionManager.setEmail(request,email);

        } catch (NoSuchAlgorithmException | ParseException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GACException("ERRORE - NOSUCHALGORITHEXCEPTION || PARSEEXCEPTION.");
            }

        } catch (SignupPasswordsMismatchException e) {

            try {
                response.sendError(500, "SPME");
            } catch (IOException ex) {
                throw new GACException("ERRORE - LE PASSWORD NON COINCIDONO.");
            }

        } catch (InvalidNameException e) {

            try {
                response.sendError(500, "INE");
            } catch (IOException ex) {
                throw new GACException("ERRORE - NOME NON VALIDO.");
            }

        } catch (InvalidPasswordException e) {

            try {
                response.sendError(500, "IPE");
            } catch (IOException ex) {
                throw new GACException("ERRORE - PASSWORD NON VALIDA.");
            }

        } catch (InvalidEmailException e) {

            try {
                response.sendError(500, "IEE");
            } catch (IOException ex) {
                throw new GACException("ERRORE - EMAIL NON VALIDA.");
            }

        } catch (NotUniqueUserException e) {

            try {
                response.sendError(500, "NUUE");
            } catch (IOException ex) {
                throw new GACException("ERRORE - UTENTE GIÀ REGISTRATO.");
            }

        }
    }

    /**
     * Gestisce la richiesta di accesso di un utente al sistema.
     *
     * @param login Stringa JSON contenente le credenziali di accesso dell'utente.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GACException Eccezione generica del gestore degli accessi (GAC).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GACException
     */
    @RequestMapping(value = "/gestoreAccessi/login", method = RequestMethod.POST)
    public void login(@RequestBody String login, HttpServletRequest request, HttpServletResponse response) throws GACException {

        JSONParser parser = new JSONParser();

        try {

            JSONObject loginJSON = (JSONObject) parser.parse(login);

            String email = (String) loginJSON.get("email");
            String password = (String) loginJSON.get("password");

            utenteService.login(email,password);

            SessionManager.setEmail(request,email);

        } catch (NoSuchAlgorithmException | ParseException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GACException("ERRORE - SQL EXCEPTION || NOSUCHALGORITHEXCEPTION || PARSEEXCEPTION.");
            }

        } catch (UserNotFoundException e) {

            try {
                response.sendError(500, "UNFE");
            } catch (IOException ex) {
                throw new GACException("ERRORE - UTENTE NON REGISTRATO.");
            }

        } catch (LoginPasswordsMismatchException e) {

            try {
                response.sendError(500, "LPME");
            } catch (IOException ex) {
                throw new GACException("ERRORE - LE PASSWORD NON COINCIDONO.");
            }

        }
    }

    /**
     * Gestisce la richiesta di logout di un utente dal sistema.
     *
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GACException Eccezione generica del gestore degli accessi (GAC).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GACException
     */
    @RequestMapping(value = "/gestoreAccessi/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws GACException {

        try {

            SessionManager.getEmail(request);

            request.getSession().invalidate();

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GACException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        }
    }
}
