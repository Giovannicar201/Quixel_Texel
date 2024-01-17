package QuixelTexel.IS.Service.GEV;

import QuixelTexel.IS.Entity.GEV.EventoEntity;
import QuixelTexel.IS.Exception.GEV.InvalidCycleValueException;
import QuixelTexel.IS.Exception.GEV.InvalidDialogValueException;
import QuixelTexel.IS.Exception.GEV.InvalidTextValueException;

public interface IstruzioneService {

    /**
     * Crea un'istruzione associata a un evento nel sistema.
     *
     * @param nome Il nome dell'istruzione.
     * @param valore Il valore dell'istruzione.
     * @param eventoEntity L'entità evento a cui associare l'istruzione.
     * @throws InvalidDialogValueException Se il valore dell'istruzione di dialogo non è valido.
     * @throws InvalidTextValueException Se il valore dell'istruzione di testo non è valido.
     * @throws InvalidCycleValueException Se il valore dell'istruzione di ciclo non è valido.
     */
    void creaIstruzione(String nome, String valore, EventoEntity eventoEntity)
            throws InvalidDialogValueException,
            InvalidTextValueException,
            InvalidCycleValueException;

    /**
     * Restituisce una stringa JSON rappresentante la lista delle istruzioni associate a un evento.
     *
     * @param eventoEntity L'entità evento.
     * @return Una stringa JSON contenente la lista delle istruzioni associate all'evento.
     */
    String visualizzaListaIstruzioniInEvento(EventoEntity eventoEntity);
}
