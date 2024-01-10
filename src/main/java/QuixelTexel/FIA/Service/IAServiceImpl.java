package QuixelTexel.FIA.Service;

import QuixelTexel.FIA.Entity.IndividuoEntity;
import QuixelTexel.FIA.Manager.EntitaManager;
import QuixelTexel.FIA.Manager.MappaManager;
import QuixelTexel.FIA.Manager.SteadyStateGAManager;
import QuixelTexel.FIA.Utility.Parser;
import org.json.simple.parser.ParseException;

public class IAServiceImpl {

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
    public String genera(String mappa, int rigaPrimoPuntoDiSelezione, int colonnaPrimoPuntoDiSelezione, int rigaSecondoPuntoDiSelezione, int colonnaSecondoPuntoDiSelezione) throws ParseException {

        MappaManager.configura(mappa,rigaPrimoPuntoDiSelezione,colonnaPrimoPuntoDiSelezione,rigaSecondoPuntoDiSelezione,colonnaSecondoPuntoDiSelezione);
        EntitaManager.configura();

        SteadyStateGAManager ssm = SteadyStateGAManager.getInstance(30,10);

        ssm.definisciPopolazioneIniziale();

        IndividuoEntity individuoMigliore = ssm.esegui();

        return Parser.convertiIndividuo(individuoMigliore.getAreaSelezionata(),rigaPrimoPuntoDiSelezione,colonnaPrimoPuntoDiSelezione);
    }
}
