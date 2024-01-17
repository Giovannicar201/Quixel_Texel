package QuixelTexel.IS.Service.GEN.GEN;

import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Exception.GEN.GEN.*;
import org.json.simple.parser.ParseException;

import java.sql.SQLException;
import java.util.List;

public interface EntitaService {

    /**
     * Crea una nuova entità con le informazioni specificate e la associa all'utente e alla cartella specificati.
     *
     * @param email L'indirizzo email dell'utente proprietario dell'entità.
     * @param nomeImmagine Il nome dell'immagine associata all'entità.
     * @param nome Il nome dell'entità da creare.
     * @param collisioni Le collisioni associate all'entità.
     * @param nomeCartella Il nome della cartella a cui associare l'entità.
     * @param nomiProprieta I nomi delle proprietà associate all'entità.
     * @param valoriProprieta I valori delle proprietà associate all'entità.
     * @throws InvalidCollisionException Se le collisioni specificate non sono valide.
     * @throws FolderNotFoundException Se la cartella specificata non esiste.
     * @throws InvalidNumberOfPropertyException Se il numero di nomi e valori delle proprietà non coincide.
     * @throws NotUniqueEntityException Se esiste già un'altra entità con lo stesso nome nella stessa cartella.
     * @throws InvalidEntityNameException Se il nome dell'entità non è valido.
     * @throws ImageNotFoundException Se l'immagine specificata non esiste.
     * @throws ImageAlreadyUsedException Se l'immagine specificata è già associata a un'altra entità.
     * @throws InvalidPropertyNameException Se uno dei nomi delle proprietà non è valido.
     * @throws InvalidPropertyValueException Se uno dei valori delle proprietà non è valido.
     * @throws NotUniquePropertyException Se esiste già un'altra entità con la stessa proprietà nella stessa cartella.
     */
    void creaEntita(String email, String nomeImmagine, String nome, String collisioni, String nomeCartella, List<String> nomiProprieta, List<String> valoriProprieta)
            throws InvalidCollisionException,
            FolderNotFoundException,
            InvalidNumberOfPropertyException,
            NotUniqueEntityException,
            InvalidEntityNameException,
            ImageNotFoundException,
            ImageAlreadyUsedException,
            InvalidPropertyNameException,
            InvalidPropertyValueException,
            NotUniquePropertyException;

    /**
     * Elimina l'entità specificata dalla mappa dell'utente.
     *
     * @param mappa La mappa da cui rimuovere l'entità.
     * @param nome Il nome dell'entità da eliminare.
     * @param email L'indirizzo email dell'utente proprietario dell'entità.
     * @return La mappa aggiornata dopo l'eliminazione dell'entità.
     * @throws EntityNotFoundException Se l'entità specificata non esiste.
     * @throws ParseException Se si verifica un errore durante la lettura della mappa.
     */
    String eliminaEntita(String mappa, String nome, String email)
            throws EntityNotFoundException,
            ParseException;

    /**
     * Restituisce la rappresentazione JSON dell'entità specificata.
     *
     * @param nome Il nome dell'entità da visualizzare.
     * @param email L'indirizzo email dell'utente proprietario dell'entità.
     * @return La rappresentazione JSON dell'entità specificata.
     * @throws EntityNotFoundException Se l'entità specificata non esiste.
     */
    String visualizzaEntita(String nome, String email) throws EntityNotFoundException;

    /**
     * Restituisce la rappresentazione JSON della lista di entità presenti nella cartella specificata.
     *
     * @param email L'indirizzo email dell'utente proprietario della cartella.
     * @param nomeCartella Il nome della cartella di cui visualizzare le entità.
     * @return La rappresentazione JSON della lista di entità nella cartella specificata.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     * @throws FolderNotFoundException Se la cartella specificata non esiste.
     */
    String visualizzaListaEntitaInCartella(String email, String nomeCartella)
            throws SQLException,
            FolderNotFoundException;

    /**
     * Restituisce la rappresentazione JSON della lista di tutte le entità dell'utente.
     *
     * @param email L'indirizzo email dell'utente proprietario delle entità.
     * @return La rappresentazione JSON della lista di tutte le entità dell'utente.
     * @throws SQLException Se si verifica un errore durante l'accesso al database.
     */
    String visualizzaListaEntita(String email) throws SQLException;

    /**
     * Ottiene l'entità dell'entità specificata tramite l'indirizzo email dell'utente proprietario e il nome dell'entità.
     *
     * @param nome Il nome dell'entità da ottenere.
     * @param email L'indirizzo email dell'utente proprietario dell'entità.
     * @return L'entità dell'entità specificata.
     */
    EntitaEntity get(String nome, String email);

    /**
     * Ottiene l'entità dell'entità specificata tramite l'identificatore univoco.
     *
     * @param id L'identificatore univoco dell'entità.
     * @return L'entità dell'entità specificata.
     */
    EntitaEntity get(int id);
}
