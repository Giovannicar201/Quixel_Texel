package QuixelTexel.FIA.Manager;

import QuixelTexel.FIA.Entity.EntitaEntity;
import QuixelTexel.FIA.Enum.LOD;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EntitaManager {
    private static final EntitaManager epm = new EntitaManager();
    private static final List<EntitaEntity> listaEntita = new ArrayList<>();

    private EntitaManager() {}

    public static EntitaManager getInstance() {
        return epm;
    }

    /**
     * Configura il manager.
     *
     * @author Angelo Antonio Prisco
     */
    public static void configura() {

        listaEntita.clear();

        epm.setOgniNumeroTotaleSullaMappa();
        epm.setOgniNumeroTotaleSullaMappaPercentuale();
        epm.setOgniNumeroTotaleSullaSelezione();
        epm.setOgniDaPiazzareSullaSelezione();
        epm.setOgniLOD();
        epm.generaLog();
    }

    /**
     * Genera un log all'interno del quale viene riportato il risultato della configurazione.
     *
     * @author Angelo Antonio Prisco
     */
    private void generaLog() {

        String filePath = "src\\main\\java\\QuixelTexel\\FIA\\Logs\\entitaLog.txt";

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(this.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imposta il numero di volte che un'entità è presente sulla mappa per ogni entità.
     *
     * @author Giovanni Carbone
     */
    private void setOgniNumeroTotaleSullaMappa() {

        MappaManager mm = MappaManager.getInstance();

        List<Integer> ids = new ArrayList<>();

        int[][] mappa = mm.getMappa();

        for (int[] riga : mappa) {
            for (int id : riga)

                if (!ids.contains(id) && id > 0) {

                    ids.add(id);
                    EntitaEntity entita = new EntitaEntity();
                    entita.setId(id);
                    entita.setNumeroTotaleSullaMappa(1);
                    listaEntita.add(entita);

                } else if (ids.contains(id) && id > 0) {

                    for (EntitaEntity entita : listaEntita)
                        if (entita.getId() == id)
                            entita.setNumeroTotaleSullaMappa(entita.getNumeroTotaleSullaMappa() + 1);

                }
        }
    }

    /**
     * Imposta il numero di volte che un'entità è presente sulla mappa in percentuale per ogni entità.
     *
     * @author Giovanni Carbone
     */
    private void setOgniNumeroTotaleSullaMappaPercentuale() {

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator('.');

        DecimalFormat df = new DecimalFormat("#.##", symbols);

        MappaManager mm = MappaManager.getInstance();
        int totaleEntitaPiazzate = mm.getTotaleEntitaPiazzate();

        for(EntitaEntity entita : listaEntita) {
            float totaleSullaMappaPercentuale = (float) entita.getNumeroTotaleSullaMappa() / totaleEntitaPiazzate;

            String formattedTotalOverMapRate = df.format(totaleSullaMappaPercentuale * 100f);

            totaleSullaMappaPercentuale = Float.parseFloat(formattedTotalOverMapRate);
            entita.setNumeroTotaleSullaMappaPercentuale(totaleSullaMappaPercentuale);
        }
    }

    /**
     * Imposta il numero di volte che un'entità deve essere piazzata all'interno dell'area selezionata.
     *
     * @author Angelo Antonio Prisco
     */
    private void setOgniNumeroTotaleSullaSelezione() {

        MappaManager mm = MappaManager.getInstance();

        int sommaNumeroTotaleSullaSelezione = 0;

        for(EntitaEntity entita : listaEntita){
            float numeroTotaleSullaSelezione = (entita.getNumeroTotaleSullaMappaPercentuale() * mm.getTotaleCelleAreaSelezione()) / 100;
            float parteFrazionale = numeroTotaleSullaSelezione - (int) numeroTotaleSullaSelezione;

            if(parteFrazionale >= 0.5f)
                entita.setNumeroTotaleSullaSelezione((int) Math.ceil(numeroTotaleSullaSelezione));
            else
                entita.setNumeroTotaleSullaSelezione((int) Math.floor(numeroTotaleSullaSelezione));

            sommaNumeroTotaleSullaSelezione += entita.getNumeroTotaleSullaSelezione();
        }

        /*
         *
         * Gestione caso in cui, a causa di arrotondamenti per difetto, il numero di volte che un'entità deve essere piazzata è minore di quello effettivo.
         *
         * */

        if(sommaNumeroTotaleSullaSelezione < mm.getTotaleCelleAreaSelezione()) {

            int totaleSullaSelezioneDaAggiungere = (mm.getTotaleCelleAreaSelezione() - sommaNumeroTotaleSullaSelezione);

            EntitaEntity entitaConPercentualeMaggiore = listaEntita.get(0);

            for(EntitaEntity entita : listaEntita)
                if(entita.getNumeroTotaleSullaMappaPercentuale() >= entitaConPercentualeMaggiore.getNumeroTotaleSullaMappaPercentuale())
                    entitaConPercentualeMaggiore = entita;

            entitaConPercentualeMaggiore.setNumeroTotaleSullaSelezione(entitaConPercentualeMaggiore.getNumeroTotaleSullaSelezione() + totaleSullaSelezioneDaAggiungere);
        }

        /*
         *
         * Gestione caso in cui, a causa di arrotondamenti per eccesso, il numero di volte che un'entità deve essere piazzata è maggiore di quello effettivo.
         *
         * */

        if(sommaNumeroTotaleSullaSelezione > mm.getTotaleCelleAreaSelezione()) {

            int totaleSullaSelezioneDaAggiungere = (sommaNumeroTotaleSullaSelezione - mm.getTotaleCelleAreaSelezione());

            EntitaEntity entitaConPercentualeMaggiore = listaEntita.get(0);

            for(EntitaEntity entita : listaEntita)
                if(entita.getNumeroTotaleSullaMappaPercentuale() >= entitaConPercentualeMaggiore.getNumeroTotaleSullaMappaPercentuale())
                    entitaConPercentualeMaggiore = entita;

            entitaConPercentualeMaggiore.setNumeroTotaleSullaSelezione(entitaConPercentualeMaggiore.getNumeroTotaleSullaSelezione() - totaleSullaSelezioneDaAggiungere);
        }

        /*
         *
         * Gestione caso in cui il numero di entità univoche è maggiore del numero di celle di cui si compone l'area selezionata.
         *
         * */

        for(int i = mm.getTotaleCelleAreaSelezione(); i < listaEntita.size(); i++)
            listaEntita.get(i).setNumeroTotaleSullaSelezione(0);
    }

    /**
     * Imposta il numero di volte che un'entità deve essere ancora piazzata all'interno dell'area selezionata.
     * Inizialmente è pari al numero di volte da piazzare sulla selezione.
     *
     * @author Giovanni Carbone
     */
    private void setOgniDaPiazzareSullaSelezione() {
        for(EntitaEntity entita : listaEntita)
            entita.setDaPiazzareSullaSelezione(entita.getNumeroTotaleSullaSelezione());
    }

    /**
     * Imposta il LOD di ogni entità.
     *
     * @author Giovanni Carbone
     */
    private void setOgniLOD() {

        EntitaEntity entitaConPercentualeMinore = listaEntita.get(0);
        EntitaEntity entitaConPercentualeMaggiore = listaEntita.get(0);

        float percentualeMinima = entitaConPercentualeMinore.getNumeroTotaleSullaMappaPercentuale();
        float percentualeMassima = entitaConPercentualeMaggiore.getNumeroTotaleSullaMappaPercentuale();

        for(int i = 1; i < listaEntita.size(); i++) {
            if (listaEntita.get(i).getNumeroTotaleSullaMappaPercentuale() < percentualeMinima)
                percentualeMinima = listaEntita.get(i).getNumeroTotaleSullaMappaPercentuale();
            else if (listaEntita.get(i).getNumeroTotaleSullaMappaPercentuale() > percentualeMassima)
                percentualeMassima = listaEntita.get(i).getNumeroTotaleSullaMappaPercentuale();
        }

        float splitCentrale = (percentualeMinima + percentualeMassima) / 2;
        float splitSinistro = (percentualeMinima + splitCentrale) / 2;
        float splitDestro = (splitCentrale + percentualeMassima) / 2;

        for(EntitaEntity entita : listaEntita){
            float numeroTotaleSullaMappaPercentuale = entita.getNumeroTotaleSullaMappaPercentuale();

            if(numeroTotaleSullaMappaPercentuale >= percentualeMinima && numeroTotaleSullaMappaPercentuale < splitSinistro)
                entita.setLOD(LOD.HIGH_LOD);
            else if(numeroTotaleSullaMappaPercentuale >= splitSinistro && numeroTotaleSullaMappaPercentuale < splitDestro)
                entita.setLOD(LOD.MEDIUM_LOD);
            else if(numeroTotaleSullaMappaPercentuale >= splitDestro && numeroTotaleSullaMappaPercentuale <= percentualeMassima)
                entita.setLOD(LOD.LOW_LOD);
        }
    }

    /**
     * Restituisce la lista di entità che ha come LOD quello passato come parametro.
     *
     * @param LOD LOD usato come filtro.
     * @return Lista di entità che hanno un LOD pari al LOD passato in input.
     * @author Angelo Antonio Prisco
     */
    public List<EntitaEntity> getEntitaByLOD(LOD LOD) {

        List<EntitaEntity> entitaByLOD = new ArrayList<>();

        for(EntitaEntity entita : listaEntita)
            if (entita.getLOD() == LOD)
                entitaByLOD.add(entita.clone());

        return entitaByLOD;
    }

    /**
     * Restituisce un intero rappresentativo della somma del numero di entità sulla selezione che hanno come LOD quello passato come parametro.
     *
     * @param LOD LOD usato come filtro.
     * @return Intero rappresentativo della somma del numero di entità sulla selezione.
     * @author Giovanni Carbone
     */
    public int getNumeroTotaleSullaSelezioneByLOD(LOD LOD) {

        List<EntitaEntity> entitaByLOD = getEntitaByLOD(LOD);

        return entitaByLOD.stream().mapToInt(EntitaEntity::getNumeroTotaleSullaSelezione).sum();
    }

    /**
     * Restituisce il LOD dato un identificativo di un'entità.
     *
     * @param id Identificativo univoco usato per la ricerca del LOD.
     * @return LOD dell'entità trovata.
     * @author Giovanni Carbone
     */
    public LOD getLODById(int id) {

        LOD LOD = null;

        for(EntitaEntity entita : listaEntita)
            if(entita.getId() == id)
                LOD = entita.getLOD();

        return LOD;
    }

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder("@log\n\n");

        for(EntitaEntity entita : listaEntita)
            result.append(entita.toString()).append("\n");

        return result.toString();
    }
}
