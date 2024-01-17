package QuixelTexel.IS.Service.GMP.GMP;

import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import QuixelTexel.IS.Exception.GMP.GMP.InvalidMapHeightException;
import QuixelTexel.IS.Exception.GMP.GMP.InvalidMapNameException;
import QuixelTexel.IS.Exception.GMP.GMP.InvalidMapWidthException;
import QuixelTexel.IS.Service.CanvasService;
import org.json.simple.parser.ParseException;

public interface MappaService extends CanvasService {

    /**
     * Crea una nuova mappa nel sistema associata a un utente.
     *
     * @param email L'email dell'utente proprietario della mappa.
     * @param nome Il nome della mappa.
     * @param lunghezza La lunghezza della mappa.
     * @param larghezza La larghezza della mappa.
     * @return Una stringa JSON rappresentante la mappa creata.
     * @throws InvalidMapNameException Se il nome della mappa non è valido.
     * @throws InvalidMapWidthException Se la larghezza della mappa non è valida.
     * @throws InvalidMapHeightException Se l'altezza della mappa non è valida.
     */
    String crea(String email, String nome, String lunghezza, String larghezza)
            throws InvalidMapNameException,
            InvalidMapWidthException,
            InvalidMapHeightException;

    /**
     * Restituisce una stringa JSON contenente le statistiche relative a una mappa.
     *
     * @param mappa La stringa JSON rappresentante la mappa.
     * @return Una stringa JSON contenente le statistiche della mappa.
     * @throws ParseException Se si verifica un errore durante il parsing della stringa JSON.
     */
    String visualizzaStatisticheMappa(String mappa) throws ParseException;

    /**
     * Ottiene l'entità mappa associata a un utente.
     *
     * @param email L'email dell'utente proprietario della mappa.
     * @return L'entità mappa associata all'utente.
     */
    MappaEntity get(String email);
}
