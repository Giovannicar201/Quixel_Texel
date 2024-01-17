package QuixelTexel.IS.Service.GMP.GCR;

import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import QuixelTexel.IS.Exception.GMP.GCR.InvalidFolderNameException;
import QuixelTexel.IS.Exception.GMP.GCR.NotUniqueFolderException;

public interface CartellaService {
    /**
     * Crea una nuova cartella per l'utente specificato.
     *
     * @param nome Il nome della nuova cartella.
     * @param email L'email dell'utente che crea la cartella.
     * @throws InvalidFolderNameException Se il nome della cartella non è valido.
     * @throws NotUniqueFolderException Se esiste già una cartella con lo stesso nome per l'utente specificato.
     */
    void creaCartella(String nome, String email) throws InvalidFolderNameException, NotUniqueFolderException;

    /**
     * Restituisce la lista di cartelle dell'utente specificato.
     *
     * @param email L'email dell'utente di cui visualizzare le cartelle.
     * @return La rappresentazione JSON della lista di cartelle dell'utente.
     */
    String visualizzaListaCartelle(String email);

    /**
     * Ottiene l'entità della cartella specificata per l'utente.
     *
     * @param nome Il nome della cartella da ottenere.
     * @param email L'email dell'utente a cui appartiene la cartella.
     * @return L'entità della cartella corrispondente al nome e all'utente specificati.
     */
    CartellaEntity get(String nome, String email);

    /**
     * Ottiene l'entità della cartella specificata per l'utente.
     *
     * @param id L'identificatore univoco della cartella.
     * @return L'entità della cartella corrispondente all'identificatore specificato.
     */
    CartellaEntity get(int id);
}
