package QuixelTexel.IS.Service.GPA.GPL;

import QuixelTexel.IS.Entity.GPA.PaletteEntity;
import QuixelTexel.IS.Exception.GPA.GPL.InvalidNumberColorException;
import QuixelTexel.IS.Exception.GPA.GPL.InvalidPaletteNameException;
import QuixelTexel.IS.Exception.GPA.GPL.PaletteCreationException;
import java.util.List;

public interface PaletteService {

    /**
     * Crea una nuova palette di colori associata a un utente.
     *
     * @param nomePalette Il nome della palette.
     * @param email L'email dell'utente proprietario della palette.
     * @param esadecimaleColori La lista degli esadecimali dei colori nella palette.
     * @throws PaletteCreationException Se si verifica un errore durante la creazione della palette.
     * @throws InvalidPaletteNameException Se il nome della palette non è valido.
     * @throws InvalidNumberColorException Se il numero di colori nella palette non è valido.
     */
    void creaPalette(String nomePalette, String email, List<String> esadecimaleColori)
            throws PaletteCreationException,
            InvalidPaletteNameException,
            InvalidNumberColorException;

    /**
     * Restituisce una stringa JSON contenente la lista delle palette associate a un utente.
     *
     * @param email L'email dell'utente proprietario delle palette.
     * @return Una stringa JSON contenente la lista delle palette dell'utente.
     */
    String visualizzaListaPalette(String email);

    /**
     * Ottiene l'entità palette associata a un utente.
     *
     * @param nomePalette Il nome della palette.
     * @param email L'email dell'utente proprietario della palette.
     * @return L'entità palette associata all'utente.
     */
    PaletteEntity get(String nomePalette, String email);
}
