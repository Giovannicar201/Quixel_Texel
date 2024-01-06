package unisa.it.is.project.fia.project.Manager;

import unisa.it.is.project.fia.project.Entity.EntitaEntity;
import unisa.it.is.project.fia.project.Enum.LOD;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        String filePath = "src\\main\\java\\it\\unisa\\IS_Project\\AI\\Logs\\entitaLog.txt";

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(this.toString());
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public String toString() {

        StringBuilder result = new StringBuilder("@log\n\n");

        for(EntitaEntity entita : listaEntita)
            result.append(entita.toString()).append("\n");

        return result.toString();
    }
}
