package unisa.it.is.project.fia.project.Manager;

import unisa.it.is.project.fia.project.Entity.EntitaEntity;
import unisa.it.is.project.fia.project.Entity.IndividuoEntity;
import unisa.it.is.project.fia.project.Entity.PopolazioneEntity;
import unisa.it.is.project.fia.project.Enum.LOD;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SteadyStateGAManager {
    private static final SteadyStateGAManager ssm = new SteadyStateGAManager();
    private int dimensionePopolazione;
    private int budgetDiRicerca;
    private final PopolazioneEntity popolazione = new PopolazioneEntity();
    private static boolean configurato;

    private SteadyStateGAManager() {}

    public static SteadyStateGAManager getInstance(int dimensionePopolazione, int budgetDiRicerca) {

        if(!configurato) {
            ssm.configura(dimensionePopolazione,budgetDiRicerca);
            configurato = true;
        }

        return ssm;
    }

    /**
     * Configura il manager.
     *
     * @param dimensionePopolazione Dimensione della popolazione iniziale.
     * @param budgetDiRicerca Numero d'iterazioni.
     * @author Angelo Antonio Prisco
     */
    private void configura(int dimensionePopolazione, int budgetDiRicerca) {

        this.dimensionePopolazione = dimensionePopolazione;
        this.budgetDiRicerca = budgetDiRicerca;
    }

    /**
     * Definisce la popolazione iniziale.
     *
     * @author Giovanni Carbone
     */
    public void definisciPopolazioneIniziale() {

        popolazione.getPopolazione().clear();

        for(int i = 0; i < dimensionePopolazione; i++) {
            IndividuoEntity individuo = generaIndividuoPopolazioneIniziale();

            individuo.getValutazione();

            popolazione.aggiungiIndividuo(individuo);
        }
    }

    /**
     * Genera un individuo della popolazione iniziale.
     *
     * @return Individuo della popolazione iniziale.
     * @author Giovanni Carbone
     */
    private IndividuoEntity generaIndividuoPopolazioneIniziale() {

        IndividuoEntity individuo = new IndividuoEntity();

        EntitaManager em = EntitaManager.getInstance();

        List<EntitaEntity> entitaHIGH_LOD = em.getEntitaByLOD(LOD.HIGH_LOD);
        List<EntitaEntity> entitaMEDIUM_LOD = em.getEntitaByLOD(LOD.MEDIUM_LOD);
        List<EntitaEntity> entitaLOW_LOD = em.getEntitaByLOD(LOD.LOW_LOD);

        if(entitaHIGH_LOD.size() != 0)
            individuo.piazzaEntitaHIGH_LOD(entitaHIGH_LOD);
        individuo.piazzaEntitaMEDIUM_LOD(entitaMEDIUM_LOD);
        individuo.piazzaEntitaLOW_LOD(entitaLOW_LOD);

        System.out.println(individuo);

        return individuo;
    }

    /**
     * Esegua la selezione su una lista d'individui, determinando i genitori ammessi al crossover. Il metodo di selezione usato è quella della truncation.
     *
     * @return Individui ammessi al crossover.
     * @author Giovanni Carbone
     */
    private List<IndividuoEntity> selezione() {

        List<IndividuoEntity> genitori = new ArrayList<>();

        List<IndividuoEntity> popolazioneOrdinata = new ArrayList<>(popolazione.getPopolazione());
        popolazioneOrdinata.sort(Comparator.comparing(IndividuoEntity::getValutazione).reversed());

        IndividuoEntity primoGenitore = popolazioneOrdinata.get(0);
        IndividuoEntity secondoGenitore = popolazioneOrdinata.get(1);

        genitori.add(primoGenitore);
        genitori.add(secondoGenitore);

        return genitori;
    }

    /**
     * Esegua la mutazione sui i figli ottenuti da un precedente crossover.
     * La mutazione consiste nello scambio di due entità casuali piazzate sulla selezione, tale metodo è definito swap.
     *
     * @param figli Figli ottenuti da un precedente crossover.
     * @return Figli mutati.
     * @author Giovanni Carbone
     */
    private List<IndividuoEntity> mutazione(List<IndividuoEntity> figli) {

        Random random = new Random();

        int rigaMassima = figli.get(0).getAreaSelezionata().length;
        int colonnaMassima = figli.get(0).getAreaSelezionata()[0].length;

        for(IndividuoEntity individuo : figli) {

            int primaRiga = random.nextInt(rigaMassima);
            int primaColonna = random.nextInt(colonnaMassima);
            int primoId = individuo.getAreaSelezionata()[primaRiga][primaColonna];
            int secondaRiga = random.nextInt(rigaMassima);
            int secondaColonna = random.nextInt(colonnaMassima);
            int secondoId = individuo.getAreaSelezionata()[secondaRiga][secondaColonna];

            individuo.getAreaSelezionata()[secondaRiga][secondaColonna] = primoId;
            individuo.getAreaSelezionata()[primaRiga][primaColonna] = secondoId;
        }

        return figli;
    }
}
