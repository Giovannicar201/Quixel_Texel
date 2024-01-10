package QuixelTexel.IS.Service.GMP.GST;

import org.json.simple.parser.ParseException;

public interface IAService {
    String genera(String mappa, String rigaPrimoPuntoDiSelezione, String colonnaPrimoPuntoDiSelezione, String rigaSecondoPuntoDiSelezione, String colonnaSecondoPuntoDiSelezione) throws ParseException;
}