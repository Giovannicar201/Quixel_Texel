package QuixelTexel.IS.Service.GST;

import org.json.simple.parser.ParseException;

public interface IAService {

    /**
     * Genera un porzione di mappa sulla base delle informazioni attualmente disponibili dalla mappa e delle informazioni sulla selezione fornite.
     *
     * @param mappa Stringa JSON rappresentante la mappa sulla quale generare una porzione.
     * @param rigaPrimoPuntoDiSelezione La riga del primo punto di selezione.
     * @param colonnaPrimoPuntoDiSelezione La colonna del primo punto di selezione.
     * @param rigaSecondoPuntoDiSelezione La riga del secondo punto di selezione.
     * @param colonnaSecondoPuntoDiSelezione La colonna del secondo punto di selezione.
     * @return Una stringa JSON rappresentante la porzione di mappa generata.
     * @throws ParseException Se si verifica un errore durante la conversione della rappresentazione della mappa.
     */

    String genera(String mappa, String rigaPrimoPuntoDiSelezione, String colonnaPrimoPuntoDiSelezione, String rigaSecondoPuntoDiSelezione, String colonnaSecondoPuntoDiSelezione) throws ParseException;
}