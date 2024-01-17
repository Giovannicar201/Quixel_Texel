package QuixelTexel.IS.Service.GST;

import QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;
import QuixelTexel.IS.Exception.GST.*;
import org.json.simple.parser.ParseException;
import java.sql.SQLException;
import java.util.List;

public interface ScatteringService {

    /**
     * Effettua una dispersione di entità una porzione di mappa selezionata dall'utente.
     *
     * @param mappa La mappa su cui effettuare la dispersione.
     * @param rigaPrimoPunto La riga del primo punto di selezione.
     * @param colonnaPrimoPunto La colonna del primo punto di selezione.
     * @param rigaSecondoPunto La riga del secondo punto di selezione.
     * @param colonnaSecondoPunto La colonna del secondo punto di selezione.
     * @param percentualeRiempimento La percentuale di riempimento della dispersione.
     * @param nomiEntita La lista dei nomi delle entità da disperdere.
     * @param prioritaEntita La lista delle priorità associate alle entità.
     * @param email L'email dell'utente che effettua l'operazione.
     * @throws InvalidFillPercentageException Se la percentuale di riempimento non è valida.
     * @throws InvalidNumberOfPriorityException Se il numero di priorità non è valido.
     * @throws InvalidPriorityPercentageSumException Se la somma delle priorità non è valida.
     * @throws SQLException Se si verifica un errore di accesso al database.
     * @throws InvalidColumnException Se la colonna specificata non è valida.
     * @throws EntityNotFoundException Se l'entità non è trovata.
     * @throws ParseException Se si verifica un errore durante il parsing dei dati.
     * @throws InvalidRowException Se la riga specificata non è valida.
     */
    String scatter(String mappa, String rigaPrimoPunto, String colonnaPrimoPunto, String rigaSecondoPunto, String colonnaSecondoPunto, String percentualeRiempimento, List<String> nomiEntita, List<String> prioritaEntita, String email)
            throws InvalidFillPercentageException,
            InvalidNumberOfPriorityException,
            InvalidPriorityPercentageSumException,
            SQLException, InvalidColumnException,
            EntityNotFoundException,
            ParseException,
            InvalidRowException;
}
