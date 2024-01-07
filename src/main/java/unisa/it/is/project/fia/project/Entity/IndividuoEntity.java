package unisa.it.is.project.fia.project.Entity;

import lombok.Getter;
import lombok.Setter;
import unisa.it.is.project.fia.project.Enum.LOD;
import unisa.it.is.project.fia.project.Manager.EntitaManager;
import unisa.it.is.project.fia.project.Manager.MappaManager;

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
     * @author Giovanni Carbone
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
}
