package QuixelTexel.IS.Service.GPA.GPL;

import QuixelTexel.IS.Entity.GPA.ColoreEntity;
import QuixelTexel.IS.Entity.GPA.PaletteEntity;

public interface ColoreService {

    /**
     * Crea un nuovo colore con il valore esadecimale specificato e lo associa alla palette specificata.
     *
     * @param esadecimale Il valore esadecimale del colore.
     * @param paletteEntity L'entità della palette a cui associare il nuovo colore.
     */
    void creaColore(String esadecimale, PaletteEntity paletteEntity);

    /**
     * Restituisce la rappresentazione JSON della lista di colori presenti nella palette specificata.
     *
     * @param paletteEntity L'entità della palette di cui visualizzare i colori.
     * @return La rappresentazione JSON della lista di colori nella palette.
     */
    String visualizzaListaColoriInPalette(PaletteEntity paletteEntity);

    /**
     * Ottiene l'entità del colore specificato tramite l'identificatore univoco.
     *
     * @param id L'identificatore univoco del colore.
     * @return L'entità del colore corrispondente all'identificatore specificato.
     */
    ColoreEntity get(int id);

    /**
     * Salva l'entità del colore nel database.
     *
     * @param coloreEntity L'entità del colore da salvare.
     */
    void save(ColoreEntity coloreEntity);
}
