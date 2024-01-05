package unisa.it.is.project.fia.project.Utility;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Parser {

    /**
     * Converte una mappa da stringa JSON a una matrice d'interi.
     *
     * @param mappa Stringa JSON contenente le informazioni relative agli identificativi e alle posizioni delle entità presenti sulla mappa.
     * @return matrice d'interi contenente all'interno delle celle gli identificativi delle entità presenti sulla mappa.
     * @throws ParseException se il parse della mappa da stringa JSON a JSONObject non va a buon fine.
     * @author Angelo Antonio Prisco
     */
    public static int[][] convertiMappa(String mappa) throws ParseException {

        JSONParser parser = new JSONParser();
        
        JSONObject mappaJSON = (JSONObject) parser.parse(mappa);
        JSONArray listaEntita = (JSONArray) mappaJSON.get("mappa");
        
        JSONObject ultimaEntitaJSON = (JSONObject) listaEntita.get(listaEntita.size() - 1);

        String ultimaRiga = (String) ultimaEntitaJSON.get("riga");
        String ultimaColonna = (String) ultimaEntitaJSON.get("colonna");

        int altezzaMappa = Integer.parseInt(ultimaRiga) + 1;
        int larghezzaMappa = Integer.parseInt(ultimaColonna) + 1;

        int[][] matriceMappa = new int[altezzaMappa][larghezzaMappa];

        for (Object obj : listaEntita) {
            JSONObject entitaJSON = (JSONObject) obj;

            String rigaEntita = (String) entitaJSON.get("riga");
            String colonnaEntita = (String) entitaJSON.get("colonna");
            Long idEntita = (Long) entitaJSON.get("id");

            int riga = Integer.parseInt(rigaEntita);
            int colonna = Integer.parseInt(colonnaEntita);
            int id = Math.toIntExact(idEntita);

            matriceMappa[riga][colonna] = id;
        }

        return matriceMappa;
    }

    /**
     * Converte un individuo da una matrice d'interi a una stringa JSON. Nella conversione modifica la posizione delle entità traslandole dalla loro posizione relativa
     * all'interno dell'area selezionata a quella assoluta all'interno dell'intera mappa.
     *
     * @param individuo Matrice d'interi contenente all'interno delle celle gli identificativi delle entità.
     * @param rigaPrimoPuntoDiSelezione Valore della riga del primo punto dell'area selezionata.
     * @param colonnaPrimoPuntoDiSelezione Valore della colonna del primo punto dell'area selezionata.
     * @return Stringa JSON contenente le informazioni relative agli identificativi e alle posizioni delle entità piazzate nell'area selezionata.
     * @author Angelo Antonio Prisco
     */
    public static String convertiIndividuo(int[][] individuo, int rigaPrimoPuntoDiSelezione, int colonnaPrimoPuntoDiSelezione) {

        JSONObject individuoJSON = new JSONObject();

        JSONArray listaEntita = new JSONArray();

        for (int riga = 0; riga < individuo.length; riga++) {
            for (int colonna = 0; colonna < individuo[riga].length; colonna++) {

                JSONObject entitaJSON = new JSONObject();

                entitaJSON.put("id",individuo[riga][colonna]);
                entitaJSON.put("riga",riga + rigaPrimoPuntoDiSelezione + "");
                entitaJSON.put("colonna",colonna + colonnaPrimoPuntoDiSelezione + "");

                listaEntita.add(entitaJSON);
            }
        }

        individuoJSON.put("entita",listaEntita);

        return individuoJSON.toString();
    }
}
