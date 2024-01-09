package unisa.it.is.project.Control.GEN.GIM;

import Utility.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import unisa.it.is.project.Exception.GEN.GIM.GIMException;
import unisa.it.is.project.Exception.GEN.GIM.InvalidFileSizeException;
import unisa.it.is.project.Exception.GEN.GIM.NotUniqueImageException;
import unisa.it.is.project.Exception.Session.MissingSessionEmailException;
import unisa.it.is.project.Service.GEN.GIM.ImmagineService;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class ImmagineControl {


    @Autowired
    public ImmagineService immagineService;

    @RequestMapping(value = "/gestoreImmagini/caricaImmagine", method = RequestMethod.POST)

    public void caricaImmagine(@RequestPart("file") MultipartFile immagine, HttpServletRequest request, HttpServletResponse response)
            throws GIMException {

        try {

            String email = SessionManager.getEmail(request);

            immagineService.caricaImmagine(immagine, email);

        } catch (IOException | SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - IOEXCEPTION || SQLEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }

        } catch (NotUniqueImageException e) {

            try {
                response.sendError(500, "NUIE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - IMMAGINE GIÀ ESISTENTE.");
            }

        } catch (InvalidFileSizeException e) {

            try {
                response.sendError(500, "IFSE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - DIMENSIONE DELL'IMMAGINE NON VALIDA.");
            }
        }
    }

    @RequestMapping(value = "/gestoreImmagini/integraImmagine", method = RequestMethod.POST)

    public void integraPixelArt(@RequestPart("file") MultipartFile immagine, HttpServletRequest request, HttpServletResponse response)
            throws GIMException {

        try {

            String email = SessionManager.getEmail(request);

            immagineService.caricaImmagine(immagine, email);

        } catch (IOException | SQLException e) {

            throw new GIMException("ERRORE - CARICA IMMAGINE IOEXCEPTION || SQLEXCEPTION.");

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - CARICA IMMAGINE IOEXCEPTION.");
            }

        } catch (InvalidFileSizeException e) {

            try {
                response.sendError(500, "IFSE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - DIMENSIONE DELL'IMMAGINE NON VALIDA.");
            }

        }
    }

    @RequestMapping(value = "/gestoreImmagini/visualizzaListaImmagini", method = RequestMethod.GET)
    @ResponseBody

    public String visualizzaListaImmagini(HttpServletRequest request, HttpServletResponse response)
            throws GIMException {

        String immagini = new JSONObject().toString();

        try {

            String email = SessionManager.getEmail(request);

            immagini = immagineService.visualizzaListaImmagini(email);

        }catch (SQLException e) {

            try {
                response.sendError(302, "NQTE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - SQLEXCEPTION.");
            }

        } catch (MissingSessionEmailException e) {

            try {
                response.sendError(302, "MSEE");
            } catch (IOException ex) {
                throw new GIMException("ERRORE - NESSUN UTENTE IN SESSIONE.");
            }
        }

        response.setContentType("text/plain");

        return immagini;
    }

}
