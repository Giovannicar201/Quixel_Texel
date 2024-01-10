package QuixelTexel.FIA.Entity;

import QuixelTexel.FIA.Manager.EntitaManager;
import QuixelTexel.FIA.Manager.MappaManager;
import QuixelTexel.FIA.Utility.Kruskal;
import lombok.Getter;
import lombok.Setter;
import QuixelTexel.FIA.Enum.LOD;

import java.util.*;

@Getter
@Setter

public class IndividuoEntity {

    private int[][] areaSelezionata;
    private float valutazione;
    private final static float PESO_PRIMA_FUNZIONE_DI_FITNESS = 60;
    private final static float PESO_SECONDA_FUNZIONE_DI_FITNESS = 40;

    /**
     * Crea un individuo rappresentativo dell'area selezionata all'interno della quale verranno piazzate le entità generate.
     *
     * @author Angelo Antonio Prisco
     */
    public IndividuoEntity() {

        MappaManager mm = MappaManager.getInstance();

        int altezzaSelezione = mm.getAltezzaSelezione();
        int larghezzaSelezione = mm.getLarghezzaSelezione();

        areaSelezionata = new int[altezzaSelezione][larghezzaSelezione];
    }

    /**
     * Controlla se una cella dell'individuo è occupata da un'entità.
     *
     * @param riga Riga usata per controllo.
     * @param colonna Colonna usata per il controllo.
     * @return Vero se la cella è libera, falso altrimenti.
     * @author Angelo Antonio Prisco
     */
    public boolean isEmpty(int riga, int colonna) {
        return areaSelezionata[riga][colonna] <= 0;
    }

    /**
     * Piazza all'interno dell'individuo le entità più importanti. Nel farlo viene calcolato un gap tra un'entità importante e quella successiva.
     * L'entità piazzata all'interno delle posizioni calcolate è scelta in maniera casuale, permettendo una diversificazione degli individui.
     *
     * @param entitaHIGH_LOD Lista delle entità più importanti.
     * @author Angelo Antonio Prisco
     */
    public void piazzaEntitaHIGH_LOD(List<EntitaEntity> entitaHIGH_LOD) {

        EntitaManager em = EntitaManager.getInstance();

        MappaManager mm = MappaManager.getInstance();
        int larghezzaSelezione = mm.getLarghezzaSelezione();
        int altezzaSelezione = mm.getAltezzaSelezione();

        Random random = new Random();

        int gap = (mm.getTotaleCelleAreaSelezione() / em.getNumeroTotaleSullaSelezioneByLOD(LOD.HIGH_LOD)) - 1;

        int riga = 0;
        int colonna = 0;;

        while(!entitaHIGH_LOD.isEmpty()) {

            int scelta = random.nextInt(entitaHIGH_LOD.size());
            EntitaEntity entita = entitaHIGH_LOD.get(scelta);

            if(entita.getDaPiazzareSullaSelezione() == 0) {

                entitaHIGH_LOD.remove(entita);

            } else {

                entita.setDaPiazzareSullaSelezione(entita.getDaPiazzareSullaSelezione() - 1);

                areaSelezionata[riga][colonna] = entita.getId();

                colonna += gap + 1;

                if(colonna >= larghezzaSelezione) {

                    riga++;
                    colonna %= larghezzaSelezione;
                }

                if(riga >= altezzaSelezione)
                    break;
            }
        }
    }

    /**
     * Piazza all'interno dell'individuo le entità mediamente importanti. Il riempimento avviene in maniera sequenziale ma le entità piazzate
     * sono scelte in maniera casuale, permettendo una diversificazione degli individui.
     *
     * @param entitaMEDIUM_LOD Lista delle entità mediamente importanti.
     * @author Giovanni Carbone
     */
    public void piazzaEntitaMEDIUM_LOD(List<EntitaEntity> entitaMEDIUM_LOD) {

        MappaManager mm = MappaManager.getInstance();
        int larghezzaSelezione = mm.getLarghezzaSelezione();
        int altezzaSelezione = mm.getAltezzaSelezione();

        Random random = new Random();

        int riga = 0;
        int colonna = 0;

        while (!entitaMEDIUM_LOD.isEmpty()) {

            if (this.isEmpty(riga, colonna)) {

                int scelta = random.nextInt(entitaMEDIUM_LOD.size());

                EntitaEntity entita = entitaMEDIUM_LOD.get(scelta);

                if (entita.getDaPiazzareSullaSelezione() == 0) {

                    entitaMEDIUM_LOD.remove(entita);

                    if(colonna == 0) {
                        riga--;
                        colonna = larghezzaSelezione;
                    }

                    colonna--;

                } else {

                    entita.setDaPiazzareSullaSelezione(entita.getDaPiazzareSullaSelezione() - 1);
                    areaSelezionata[riga][colonna] = entita.getId();

                }
            }

            colonna++;

            if(colonna >= larghezzaSelezione) {

                riga++;
                colonna %= larghezzaSelezione;
            }

            if(riga >= altezzaSelezione)
                break;
        }
    }

    /**
     * Piazza all'interno dell'individuo le entità meno importanti. Il riempimento avviene in maniera sequenziale ma le entità piazzate
     * sono scelte in maniera casuale, permettendo una diversificazione degli individui.
     *
     * @param entitaLOW_LOD Lista delle entità meno importanti.
     * @author Giovanni Carbone
     */
    public void piazzaEntitaLOW_LOD(List<EntitaEntity> entitaLOW_LOD) {

        MappaManager mm = MappaManager.getInstance();
        int larghezzaSelezione = mm.getLarghezzaSelezione();
        int altezzaSelezione = mm.getAltezzaSelezione();

        Random random = new Random();

        int riga = 0;
        int colonna = 0;

        while (!entitaLOW_LOD.isEmpty()) {

            if (this.isEmpty(riga, colonna)) {

                int scelta = random.nextInt(entitaLOW_LOD.size());

                EntitaEntity entita = entitaLOW_LOD.get(scelta);

                if (entita.getDaPiazzareSullaSelezione() == 0) {

                    entitaLOW_LOD.remove(entita);

                    if(colonna == 0) {
                        riga--;
                        colonna = mm.getLarghezzaSelezione();
                    }

                    colonna--;

                } else {
                    entita.setDaPiazzareSullaSelezione(entita.getDaPiazzareSullaSelezione() - 1);
                    areaSelezionata[riga][colonna] = entita.getId();
                }
            }

            colonna++;

            if(colonna >= larghezzaSelezione) {

                riga++;
                colonna %= larghezzaSelezione;
            }

            if(riga >= altezzaSelezione)
                break;
        }
    }

    /**
     * Calcola la prima funzione di fitness a partire dalle caratteristiche dell'individuo.
     * La prima funzione di fitness è rappresentata dall valore ottenuto sommando gli archi del maximum spanning tree ottenuto a partire
     * da un grafo completamente connesso, il quale ha come vertici tutte le entità più importanti. Tale valore va massimizzato siccome è desiderabile che le
     * entità più importanti siano quanto più distanti tra di loro.
     *
     * @author Angelo Antonio Prisco
     */
    private float primaFunzioneDiFitness() {

        EntitaManager em = EntitaManager.getInstance();

        List<String> vertici = new ArrayList<>();
        List<ArcoEntity> archi = new ArrayList<>();

        List<EntitaPiazzataEntity> entita = new ArrayList<>();

        for (int riga = 0; riga < areaSelezionata.length; riga++) {
            for (int colonna = 0; colonna < areaSelezionata[riga].length; colonna++) {

                EntitaPiazzataEntity entitaPiazzata = new EntitaPiazzataEntity(areaSelezionata[riga][colonna], riga, colonna);

                if (em.getLODById(entitaPiazzata.getId()) == LOD.HIGH_LOD) {

                    entita.add(entitaPiazzata);
                    vertici.add(entitaPiazzata.toString());

                }
            }
        }

        for(int i = 0; i < entita.size(); i++) {

            EntitaPiazzataEntity entitaPiazzataV = entita.get(i);

            for(int j = i + 1; j < entita.size(); j++) {

                EntitaPiazzataEntity entitaPiazzataW = entita.get(j);

                ArcoEntity arco = new ArcoEntity(entitaPiazzataV,entitaPiazzataW);

                archi.add(arco);
            }
        }

        return Kruskal.computaMaxSTCompletamenteConnesso(vertici,archi);
    }

    /**
     * Calcola la seconda funzione di fitness a partire dalle caratteristiche dell'individuo.
     * La seconda funzione di fitness è rappresentata dall valore ottenuto sommando gli archi del minimum spanning tree ottenuto a partire
     * da un grafo completamente connesso, il quale ha come vertici tutte le entità meno importanti. Tale valore va minimizzato siccome è desiderabile che le
     * entità meno importanti siano quanto meno distanti tra di loro.
     *
     * @author Angelo Antonio Prisco
     */
    private float secondaFunzioneDiFitness() {

        EntitaManager em = EntitaManager.getInstance();

        List<String> vertici = new ArrayList<>();
        List<ArcoEntity> archi = new ArrayList<>();

        List<EntitaPiazzataEntity> entita = new ArrayList<>();

        for (int riga = 0; riga < areaSelezionata.length; riga++) {
            for (int colonna = 0; colonna < areaSelezionata[riga].length; colonna++) {

                EntitaPiazzataEntity entitaPiazzata = new EntitaPiazzataEntity(areaSelezionata[riga][colonna], riga, colonna);

                if (em.getLODById(entitaPiazzata.getId()) == LOD.LOW_LOD) {

                    entita.add(entitaPiazzata);
                    vertici.add(entitaPiazzata.toString());

                }
            }
        }

        for(int i = 0; i < entita.size(); i++) {

            EntitaPiazzataEntity entitaPiazzataV = entita.get(i);

            for(int j = i + 1; j < entita.size(); j++) {

                EntitaPiazzataEntity entitaPiazzataW = entita.get(j);

                ArcoEntity arco = new ArcoEntity(entitaPiazzataV,entitaPiazzataW);

                archi.add(arco);
            }
        }

        return Kruskal.computaMinSTCompletamenteConnesso(vertici,archi);
    }

    /**
     * Calcola la valutazione finale dell'individuo sulla base della prima e della seconda funzione di fitness combinate.
     *
     * @author Giovanni Carbone
     */
    public float getValutazione() {

        valutazione = (PESO_PRIMA_FUNZIONE_DI_FITNESS * primaFunzioneDiFitness() + PESO_SECONDA_FUNZIONE_DI_FITNESS * secondaFunzioneDiFitness()) / (PESO_PRIMA_FUNZIONE_DI_FITNESS + PESO_SECONDA_FUNZIONE_DI_FITNESS);

        return valutazione;
    }

    /**
     * Controlla se l'individuo è valido, ovvero se rispetta tutti i vincoli relativi al numero di entità piazzate per ogni identificativo.
     *
     * @param mappaDelleOccorrenze Map che contiene il numero di occorrenze di un'entità all'interno dell'individuo.
     * @author Giovanni Carbone
     */
    public boolean isValid(Map<Integer,Integer> mappaDelleOccorrenze) {

        for (Map.Entry<Integer, Integer> entry : mappaDelleOccorrenze.entrySet())
            if (entry.getValue() != 0)
                return false;

        return true;
    }

    /**
     * Recupera un individuo non valido rendendolo valido.
     *
     * @param mappaDelleOccorrenze Map che contiene il numero di occorrenze di un'entità all'interno dell'individuo.
     * @author Giovanni Carbone
     */
    public void recovery(Map<Integer,Integer> mappaDelleOccorrenze) {

        EntitaManager em = EntitaManager.getInstance();

        int idDaRimpiazzare = 0;
        int nuovoId = 0;

        while(!this.isValid(mappaDelleOccorrenze)) {

            for (Map.Entry<Integer, Integer> entry : mappaDelleOccorrenze.entrySet())
                if (entry.getValue() > 0) {

                    nuovoId = entry.getKey();
                    entry.setValue(entry.getValue() - 1);

                    break;
                }

            for (Map.Entry<Integer, Integer> entry : mappaDelleOccorrenze.entrySet())
                if (entry.getValue() < 0) {

                    idDaRimpiazzare = entry.getKey();
                    entry.setValue(entry.getValue() + 1);

                    break;
                }

            this.rimpiazza(idDaRimpiazzare,nuovoId);
        }
    }

    /**
     * Rimpiazza un identificativo di un'entità piazzata con l'identificativo di una seconda entità.
     *
     * @author Giovanni Carbone
     */
    private void rimpiazza(int idDaRimpiazzare, int nuovoId) {

        for (int riga = 0; riga < areaSelezionata.length; riga++) {
            for (int colonna = 0; colonna < areaSelezionata[0].length; colonna++) {

                if (areaSelezionata[riga][colonna] == idDaRimpiazzare) {

                    areaSelezionata[riga][colonna] = nuovoId;

                    return;
                }
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder risultato = new StringBuilder("@individual::");

        for (int[] riga : areaSelezionata) {
            risultato.append("\n");
            for (int id : riga)
                risultato.append(id).append("\t");
        }

        risultato.append("\n@valutazione::").append(valutazione).append("\n");

        return risultato.toString();
    }
}
