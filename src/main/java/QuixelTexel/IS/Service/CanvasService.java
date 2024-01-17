package QuixelTexel.IS.Service;

import QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;
import QuixelTexel.IS.Exception.GEN.GEN.FolderNotFoundException;
import QuixelTexel.IS.Exception.GPA.GPL.PaletteNotFoundException;
import QuixelTexel.IS.Exception.GST.InvalidColumnException;
import QuixelTexel.IS.Exception.GST.InvalidRowException;
import org.json.simple.parser.ParseException;
import java.sql.SQLException;

public interface CanvasService {

    /**
     * Posiziona un elemento sulla mappa o sulla pixel art.
     *
     * @param canvas La mappa o la pixel art su cui posizionare l'elemento.
     * @param elemento L'elemento (entità/colore) da posizionare.
     * @param riga La riga in cui posizionare l'elemento.
     * @param colonna La colonna in cui posizionare l'elemento.
     * @param email L'email dell'utente che effettua l'operazione.
     * @return La mappa o la pixel art modificata con l'elemento posizionato.
     * @throws SQLException Se si verifica un errore di accesso al database.
     * @throws EntityNotFoundException Se l'entità specificata non è trovata.
     * @throws InvalidRowException Se la riga specificata non è valida.
     * @throws InvalidColumnException Se la colonna specificata non è valida.
     * @throws ParseException Se si verifica un errore durante il parsing dei dati.
     */
    String piazza(String canvas, String elemento, String riga, String colonna, String email) throws SQLException, EntityNotFoundException, InvalidRowException, InvalidColumnException, ParseException;

    /**
     * Rimuove l'elemento dalla mappa specificata.
     *
     * @param canvas La mappa o la pixel art su cui rimuovere l'elemento.
     * @param riga La riga da cui rimuovere l'elemento.
     * @param colonna La colonna da cui rimuovere l'elemento.
     * @param email L'email dell'utente che effettua l'operazione.
     * @return La mappa o la pixel art modificata con l'elemento rimosso.
     * @throws InvalidRowException Se la riga specificata non è valida.
     * @throws InvalidColumnException Se la colonna specificata non è valida.
     * @throws ParseException Se si verifica un errore durante il parsing dei dati.
     */
    String rimuovi(String canvas, String riga, String colonna, String email) throws InvalidRowException, InvalidColumnException, ParseException;

    /**
     * Visualizza la collezione di elementi (entità) in base al tipo specificato.
     *
     * @param email L'email dell'utente che richiede la visualizzazione.
     * @param nome Il nome della collezione da visualizzare (cartella o palette).
     * @return La collezione di elementi (entità/colori) in formato JSON.
     * @throws SQLException Se si verifica un errore di accesso al database.
     * @throws FolderNotFoundException Se la cartella specificata non è trovata.
     * @throws PaletteNotFoundException Se la palette specificata non è trovata.
     */
    String visualizzaCollezioneElementi(String email, String nome) throws SQLException, FolderNotFoundException, PaletteNotFoundException;
}
