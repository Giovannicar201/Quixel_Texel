package unisa.it.is.project.fia.project.Manager;

import unisa.it.is.project.fia.project.Utility.Parser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MappaManager {
    private static final MappaManager mm = new MappaManager();
    private int rigaPrimoPuntoDiSelezione;
    private int colonnaPrimoPuntoDiSelezione;
    private int rigaSecondoPuntoDiSelezione;
    private int colonnaSecondoPuntoDiSelezione;
    private int totaleEntitaPiazzate;
    private int larghezzaSelezione;
    private int altezzaSelezione;
    private int totaleCelleAreaSelezione;
    private int[][] mappa;

    private MappaManager() { }

    public static MappaManager getInstance() {
        return mm;
    }

    /**
     * Configura il manager.
     *
     * @param mappa Stringa JSON contenente le informazioni relative agli identificativi e alle posizioni delle entità presenti sulla mappa.
     * @param rigaPrimoPuntoDiSelezione Valore della riga del primo punto di selezione.
     * @param colonnaPrimoPuntoDiSelezione Valore della colonna del primo punto di selezione.
     * @param rigaSecondoPuntoDiSelezione Valore della riga del secondo punto di selezione.
     * @param colonnaSecondoPuntoDiSelezione Valore della colonna del secondo punto di selezione.
     * @throws ParseException se il parse della mappa da stringa JSON a JSONObject non va a buon fine.
     * @author Giovanni Carbone
     */
    public static void configura(String mappa, int rigaPrimoPuntoDiSelezione, int colonnaPrimoPuntoDiSelezione, int rigaSecondoPuntoDiSelezione, int colonnaSecondoPuntoDiSelezione) throws ParseException {

        mm.setRigaPrimoPuntoDiSelezione(rigaPrimoPuntoDiSelezione);
        mm.setColonnaPrimoPuntoDiSelezione(colonnaPrimoPuntoDiSelezione);
        mm.setRigaSecondoPuntoDiSelezione(rigaSecondoPuntoDiSelezione);
        mm.setColonnaSecondoPuntoDiSelezione(colonnaSecondoPuntoDiSelezione);
        mm.setMappa(mappa);
        mm.setLarghezzaSelezione();
        mm.setAltezzaSelezione();
        mm.setTotaleCelleAreaSelezione();
        mm.setTotaleEntitaPiazzate();
        mm.generaLog();
    }

    /**
     * Genera un log all'interno del quale viene riportato il risultato della configurazione.
     *
     * @author Giovanni Carbone
     */
    private void generaLog() {

        String filePath = "src\\main\\java\\it\\unisa\\IS_Project\\AI\\Logs\\mappaLog.txt";

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(this.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRigaPrimoPuntoDiSelezione(int rigaPrimoPuntoDiSelezione) {
        this.rigaPrimoPuntoDiSelezione = rigaPrimoPuntoDiSelezione;
    }

    public void setColonnaPrimoPuntoDiSelezione(int colonnaPrimoPuntoDiSelezione) {
        this.colonnaPrimoPuntoDiSelezione = colonnaPrimoPuntoDiSelezione;
    }

    public void setRigaSecondoPuntoDiSelezione(int rigaSecondoPuntoDiSelezione) {
        this.rigaSecondoPuntoDiSelezione = rigaSecondoPuntoDiSelezione;
    }

    public void setColonnaSecondoPuntoDiSelezione(int colonnaSecondoPuntoDiSelezione) {
        this.colonnaSecondoPuntoDiSelezione = colonnaSecondoPuntoDiSelezione;
    }

    public void setMappa(String mappa) throws ParseException {
        this.mappa = Parser.convertiMappa(mappa);
    }

    public int getTotaleEntitaPiazzate() {
        return totaleEntitaPiazzate;
    }

    private void setTotaleEntitaPiazzate() {

        totaleEntitaPiazzate = 0;

        for (int[] riga : mappa)
            for (int id : riga)
                if (id > 0)
                    totaleEntitaPiazzate++;
    }

    public int getLarghezzaSelezione() {
        return larghezzaSelezione;
    }

    private void setLarghezzaSelezione() {
        larghezzaSelezione = colonnaSecondoPuntoDiSelezione - colonnaPrimoPuntoDiSelezione + 1;
    }

    public int getAltezzaSelezione() {
        return altezzaSelezione;
    }

    private void setAltezzaSelezione() {
        altezzaSelezione = rigaSecondoPuntoDiSelezione - rigaPrimoPuntoDiSelezione + 1;
    }

    public int getTotaleCelleAreaSelezione() {
        return totaleCelleAreaSelezione;
    }

    private void setTotaleCelleAreaSelezione() {
        totaleCelleAreaSelezione = larghezzaSelezione * altezzaSelezione;
    }

    public int[][] getMappa() {
        return mappa;
    }

    @Override
    public String toString() {

        return "@log\n\n" +
                "@primoPuntoDiSelezione::(" + rigaPrimoPuntoDiSelezione + "," + colonnaPrimoPuntoDiSelezione + ")" + " " +
                "@secondPuntoDiSelezione::(" + rigaSecondoPuntoDiSelezione + "," + colonnaSecondoPuntoDiSelezione + ")" + " " +
                "@totaleEntitaPiazzate::" + totaleEntitaPiazzate + " " +
                "@larghezzaSelezione::" + larghezzaSelezione + " " +
                "@altezzaSelezione::" + altezzaSelezione + " " +
                "@totaleCelleAreaSelezione::" + totaleCelleAreaSelezione;
    }
}
