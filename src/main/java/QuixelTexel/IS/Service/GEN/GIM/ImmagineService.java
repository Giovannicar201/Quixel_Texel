package QuixelTexel.IS.Service.GEN.GIM;

import QuixelTexel.IS.Entity.GEN.GIM.ImmagineEntity;
import QuixelTexel.IS.Exception.GEN.GIM.InvalidFileSizeException;
import QuixelTexel.IS.Exception.GEN.GIM.NotUniqueImageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface ImmagineService {

    /**
     * Carica un'immagine associata a un utente nel sistema.
     *
     * @param immagine L'immagine da caricare.
     * @param email L'email dell'utente a cui associare l'immagine.
     * @throws SQLException Se si verifica un errore durante l'interazione con il database.
     * @throws IOException Se si verifica un errore durante la lettura o scrittura del file dell'immagine.
     * @throws InvalidFileSizeException Se l'immagine supera le dimensioni consentite.
     * @throws NotUniqueImageException Se l'immagine con lo stesso nome esiste già per l'utente.
     */
    void caricaImmagine(MultipartFile immagine, String email)
            throws SQLException,
            IOException,
            InvalidFileSizeException,
            NotUniqueImageException;

    /**
     * Restituisce una stringa JSON rappresentante la lista delle immagini associate a un utente.
     *
     * @param email L'email dell'utente.
     * @return Una stringa JSON contenente la lista delle immagini associate all'utente.
     * @throws SQLException Se si verifica un errore durante l'interazione con il database.
     */
    String visualizzaListaImmagini(String email) throws SQLException;

    /**
     * Restituisce un'entità immagine basata sul nome e l'email dell'utente.
     *
     * @param nome Il nome dell'immagine.
     * @param email L'email dell'utente a cui è associata l'immagine.
     * @return L'entità immagine corrispondente.
     */
    ImmagineEntity get(String nome, String email);

    /**
     * Salva un'entità immagine nel sistema.
     *
     * @param immagineEntity L'entità immagine da salvare.
     */
    void save(ImmagineEntity immagineEntity);
}
