package QuixelTexel.IS.Service.GPA.GPA;

import QuixelTexel.IS.Exception.GEN.GIM.NotUniqueImageException;
import QuixelTexel.IS.Exception.GPA.GPA.InvalidPixelArtNameException;
import QuixelTexel.IS.Service.CanvasService;
import org.json.simple.parser.ParseException;

import java.sql.SQLException;

public interface PixelArtService extends CanvasService {

    /**
     * Crea una nuova pixel art associata a un utente.
     *
     * @param email L'email dell'utente proprietario della pixel art.
     * @param nome Il nome della pixel art.
     * @param altezza L'altezza della pixel art.
     * @param larghezza La larghezza della pixel art.
     * @throws InvalidPixelArtNameException Se il nome della pixel art non è valido.
     * @return Una stringa rappresentante la pixel art vuota appena creata.
     */
    String crea(String email, String nome, String altezza, String larghezza) throws InvalidPixelArtNameException;

    /**
     * Integra un'immagine nella pixel art associata a un utente.
     *
     * @param immagine L'immagine da integrare nella pixel art.
     * @param nomeFoto Il nome dell'immagine.
     * @param email L'email dell'utente proprietario della pixel art.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     * @throws NotUniqueImageException Se l'immagine è già presente nella pixel art.
     * @throws ParseException Se si verifica un errore durante la conversione dei dati.
     */
    void integraPixelArt(String immagine, String nomeFoto, String email)
            throws SQLException,
            NotUniqueImageException,
            ParseException;
}
