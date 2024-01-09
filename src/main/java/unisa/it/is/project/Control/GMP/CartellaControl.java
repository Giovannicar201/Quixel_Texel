package unisa.it.is.project.Control.GMP;

import Utility.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import unisa.it.is.project.Exception.GMP.*;
import unisa.it.is.project.Exception.Session.*;
import unisa.it.is.project.Service.GMP.CartellaService;

import java.io.IOException;


@Controller
public class CartellaControl {

    @Autowired
    public CartellaService cartellaService;

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
