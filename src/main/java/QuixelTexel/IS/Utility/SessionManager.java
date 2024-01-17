package QuixelTexel.IS.Utility;

import QuixelTexel.IS.Exception.Session.*;
import jakarta.servlet.http.HttpServletRequest;

public class SessionManager {
    public static void setEmail(HttpServletRequest request, String email) {

        request.getSession().setAttribute("email", email);

    }

    public static String getEmail(HttpServletRequest request) throws MissingSessionEmailException {
        String email = (String) request.getSession().getAttribute("email");

        if(email == null)
            throw new MissingSessionEmailException("ERRORE - NESSUN UTENTE IN SESSIONE.");

        return email;
    }

    public static void setMappa(HttpServletRequest request, String mappa) {

        request.getSession().removeAttribute("mappa");

        request.getSession().setAttribute("mappa", mappa);
    }

    public static String getMappa(HttpServletRequest request)
            throws MissingSessionMapException {

        String mappa = (String) request.getSession().getAttribute("mappa");

        if(mappa == null)
            throw new MissingSessionMapException("ERRORE - NESSUNA MAPPA IN SESSIONE.");

        return mappa;
    }

    public static void setPixelArt(HttpServletRequest request, String pixelArt) {

        request.getSession().setAttribute("pixelArt", pixelArt);

    }

    public static String getPixelArt(HttpServletRequest request) throws MissingSessionPixelArtException {
        String pixelArt = (String) request.getSession().getAttribute("pixelArt");

        if(pixelArt == null)
            throw new MissingSessionPixelArtException("ERRORE - NESSUNA PIXEL ART IN SESSIONE.");

        return pixelArt;
    }

    public static void setSelezioneMappa(HttpServletRequest request, String selezioneMappa) {

        request.getSession().setAttribute("selezioneMappa", selezioneMappa);

    }

    public static String getSelezioneMappa(HttpServletRequest request) throws MissingSessionMapSelectionException {
        String selezioneMappa = (String) request.getSession().getAttribute("selezioneMappa");

        if(selezioneMappa == null)
            throw new MissingSessionMapSelectionException("ERRORE - NESSUNA SELEZIONE MAPPA IN SESSIONE.");

        return selezioneMappa;
    }
}
