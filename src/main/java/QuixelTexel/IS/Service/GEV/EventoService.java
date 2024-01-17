package QuixelTexel.IS.Service.GEV;

import QuixelTexel.IS.Entity.GEV.EventoEntity;
import QuixelTexel.IS.Exception.GEV.*;

import java.util.List;

public interface EventoService {

    /**
     * Crea un nuovo evento con le informazioni specificate e lo associa all'utente.
     *
     * @param email L'indirizzo email dell'utente proprietario dell'evento.
     * @param nome Il nome dell'evento da creare.
     * @param riga La riga della mappa in cui posizionare l'evento.
     * @param colonna La colonna della mappa in cui posizionare l'evento.
     * @param nomiIstruzioni I nomi delle istruzioni associate all'evento.
     * @param valoriIstruzioni I valori delle istruzioni associate all'evento.
     * @throws InvalidEventNameException Se il nome dell'evento non è valido.
     * @throws NotUniqueEventException Se esiste già un altro evento con lo stesso nome nella mappa.
     * @throws InvalidNumberOfInstructionsException Se il numero di nomi e valori delle istruzioni non coincide.
     * @throws InvalidPositionException Se la posizione specificata non è valida sulla mappa.
     * @throws InvalidNestedCycleException Se si verifica un ciclo annidato non valido.
     * @throws InvalidCyclesException Se il numero di cicli non è valido.
     * @throws InvalidCycleValueException Se il valore di un ciclo non è valido.
     * @throws InvalidTextValueException Se il valore di un'istruzione di testo non è valido.
     * @throws InvalidDialogValueException Se il valore di un'istruzione di dialogo non è valido.
     */
    void creaEvento(String email, String nome, String riga, String colonna, List<String> nomiIstruzioni, List<String> valoriIstruzioni)
            throws InvalidEventNameException,
            NotUniqueEventException,
            InvalidNumberOfInstructionsException,
            InvalidPositionException,
            InvalidNestedCycleException,
            InvalidCyclesException,
            InvalidCycleValueException,
            InvalidTextValueException,
            InvalidDialogValueException;

    /**
     * Restituisce la rappresentazione JSON della lista di eventi dell'utente.
     *
     * @param email L'indirizzo email dell'utente proprietario degli eventi.
     * @return La rappresentazione JSON della lista di eventi dell'utente.
     */
    String visualizzaListaEventi(String email);

    /**
     * Ottiene l'entità dell'evento specificato tramite il nome dell'evento e l'indirizzo email dell'utente proprietario.
     *
     * @param nomeEvento Il nome dell'evento da ottenere.
     * @param email L'indirizzo email dell'utente proprietario dell'evento.
     * @return L'entità dell'evento specificato.
     */
    EventoEntity get(String nomeEvento, String email);
}
