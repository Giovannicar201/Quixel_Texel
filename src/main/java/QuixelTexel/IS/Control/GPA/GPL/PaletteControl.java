package QuixelTexel.IS.Control.GPA.GPL;

import QuixelTexel.IS.Exception.GPA.GPL.GPLException;
import QuixelTexel.IS.Exception.GPA.GPL.InvalidNumberColorException;
import QuixelTexel.IS.Exception.GPA.GPL.InvalidPaletteNameException;
import QuixelTexel.IS.Exception.GPA.GPL.PaletteCreationException;
import QuixelTexel.IS.Exception.Session.MissingSessionEmailException;
import QuixelTexel.IS.Service.GPA.GPL.PaletteService;
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
public class PaletteControl {

    @Autowired
    private PaletteService paletteService;

    /**
     * Gestisce la richiesta di creazione di una nuova palette. La richiesta deve includere
     *
     * @param palette Stringa JSON contenente il nome e gli esadecimali della palette.
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @throws GPLException Eccezione generica del gestore delle palette (GPL).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GPLException
     */
    @RequestMapping(value = "/pixelArt/creaPalette", method = RequestMethod.POST)
    public void creaPalette(@RequestBody String palette, HttpServletRequest request, HttpServletResponse response) throws GPLException {

        JSONParser parser = new JSONParser();

        try {

            String email = SessionManager.getEmail(request);

            JSONObject paletteJSON = (JSONObject) parser.parse(palette);

            JSONArray esadecimali = (JSONArray) paletteJSON.get("esadecimali");

            String nome = (String) paletteJSON.get("nome");

            List<String> valoriEsadecimali = new ArrayList<>();

            for (Object obj : esadecimali) {

                String esadecimale = (String) obj;

                valoriEsadecimali.add(esadecimale);
            }

            paletteService.creaPalette(nome,email,valoriEsadecimali);

        } catch(ParseException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GPLException("ERRORE - PARSEEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GPLException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (PaletteCreationException e){

            try {
                response.sendError(500, "PCE");
            } catch (IOException ex) {
                throw new GPLException("ERRORE - PALETTE NON ESISTENTE.");
            }

        } catch (InvalidPaletteNameException e) {

            try {
                response.sendError(500,"IPNE");
            } catch (IOException ex){
                throw new GPLException("ERRORE - NOME NON VALIDO");
            }

        } catch (InvalidNumberColorException e) {

            try {
                response.sendError(500,"INCE");
            } catch (IOException ex){
                throw new GPLException("ERRORE - NUMERO DI COLORI NON VALIDO");
            }

        }
    }

    /**
     * Gestisce la richiesta di visualizzazione della lista delle palette di un utente.
     *
     * @param request Oggetto HttpServletRequest che rappresenta la richiesta HTTP.
     * @param response Oggetto HttpServletResponse che rappresenta la risposta HTTP.
     * @return Stringa JSON contenente i nomi delle palette dell'utente.
     * @throws GPLException Eccezione generica del gestore delle palette (GPL).
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see GPLException
     */
    @RequestMapping(value = "/pixelArt/visualizzaListaPalette", method = RequestMethod.POST)
    @ResponseBody
    public String visualizzaListaPalette(HttpServletRequest request,HttpServletResponse response) throws GPLException {

        String nomiPalette = new JSONObject().toString();

        try {

            String email = SessionManager.getEmail(request);

            nomiPalette = paletteService.visualizzaListaPalette(email);

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GPLException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }
        }

        response.setContentType("text/plain");

        return nomiPalette;
    }
}

