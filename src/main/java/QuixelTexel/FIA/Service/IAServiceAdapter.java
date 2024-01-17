package QuixelTexel.FIA.Service;

import QuixelTexel.IS.Service.GST.IAService;
import org.json.simple.parser.ParseException;

public class IAServiceAdapter implements IAService {
    private final IAServiceImpl iaService;

    public IAServiceAdapter() {
        iaService = new IAServiceImpl();
    }

    /**
     * Genera un porzione di mappa.
     *
     * @param mappa Stringa JSON contenente le informazioni relative agli identificativi e alle posizioni delle entità presenti sulla mappa.
     * @param rigaPrimoPuntoDiSelezione Valore della riga del primo punto di selezione.
     * @param colonnaPrimoPuntoDiSelezione Valore della colonna del primo punto di selezione.
     * @param rigaSecondoPuntoDiSelezione Valore della riga del secondo punto di selezione.
     * @param colonnaSecondoPuntoDiSelezione Valore della colonna del secondo punto di selezione.
     * @throws ParseException se il parse della mappa da stringa JSON a JSONObject non va a buon fine.
     * @author Angelo Antonio Prisco
     */
    @Override
    public String genera(String mappa, String rigaPrimoPuntoDiSelezione, String colonnaPrimoPuntoDiSelezione, String rigaSecondoPuntoDiSelezione, String colonnaSecondoPuntoDiSelezione) throws ParseException {

        int rigaPrimoPunto = Integer.parseInt(rigaPrimoPuntoDiSelezione);
        int colonnaPrimoPunto = Integer.parseInt(colonnaPrimoPuntoDiSelezione);
        int rigaSecondoPunto = Integer.parseInt(rigaSecondoPuntoDiSelezione);
        int colonnaSecondoPunto = Integer.parseInt(colonnaSecondoPuntoDiSelezione);

        return iaService.genera(mappa,rigaPrimoPunto,colonnaPrimoPunto,rigaSecondoPunto,colonnaSecondoPunto);
    }
}
