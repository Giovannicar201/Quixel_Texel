package unisa.it.is.project.fia.project.Manager;

import unisa.it.is.project.fia.project.Entity.EntitaEntity;
import unisa.it.is.project.fia.project.Entity.IndividuoEntity;
import unisa.it.is.project.fia.project.Entity.PopolazioneEntity;
import unisa.it.is.project.fia.project.Enum.LOD;

import java.util.*;

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
     * Esegue uno steady state GA.
     *
     * @return Individuo migliore generato.
     * @author Angelo Antonio Prisco
     */
    public IndividuoEntity esegui() {

        IndividuoEntity individuoMigliore = popolazione.getPopolazione().get(0);

        for(int i = 0; i < budgetDiRicerca; i++) {

            List<IndividuoEntity> individui = this.mutazione(this.crossover(this.selezione()));

            popolazione.getPopolazione().sort((primoIndividuo, secondoIndividuo) -> {
                float primaValutazione = primoIndividuo.getValutazione();
                float secondaValutazione = secondoIndividuo.getValutazione();
                return Float.compare(secondaValutazione, primaValutazione);
            });

            IndividuoEntity primoIndividuoPeggiore = popolazione.getPopolazione().get(popolazione.getPopolazione().size() - 1);
            IndividuoEntity secondoIndividuoPeggiore = popolazione.getPopolazione().get(popolazione.getPopolazione().size() - 2);

            for(IndividuoEntity individuo : individui) {
                if(individuo.getValutazione() >= primoIndividuoPeggiore.getValutazione()) {
                    popolazione.getPopolazione().remove(primoIndividuoPeggiore);
                    popolazione.getPopolazione().add(individuo);
                } else if(individuo.getValutazione() >= secondoIndividuoPeggiore.getValutazione()) {
                    popolazione.getPopolazione().remove(secondoIndividuoPeggiore);
                    popolazione.getPopolazione().add(individuo);
                }
            }

            for(IndividuoEntity individuo : popolazione.getPopolazione())
                if(individuo.getValutazione() >= individuoMigliore.getValutazione())
                    individuoMigliore = individuo;
        }

        System.out.println(individuoMigliore);

        return individuoMigliore;
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
     * Esegue il crossover tra due individui. Il metodo di crossover usato è detto uniform.
     *
     * @param  genitori Coppia d'individui ammessi al crossover.
     * @return Coppia d'individui figli.
     * @author Angelo Antonio Prisco
     */
    private List<IndividuoEntity> crossover(List<IndividuoEntity> genitori) {

        List<IndividuoEntity> figli = new ArrayList<>();
        Random random = new Random();

        int[][] primoGenitore = genitori.get(0).getAreaSelezionata();
        int[][] secondoGenitore = genitori.get(1).getAreaSelezionata();

        System.out.println(genitori.get(0));
        System.out.println(genitori.get(1));

        int altezza = primoGenitore.length;
        int larghezza = primoGenitore[0].length;

        int[][] primoFiglio = new int[altezza][larghezza];
        int[][] secondoFiglio = new int[altezza][larghezza];

        Map<Integer, Integer> primaMappaDelleOccorrenze = new HashMap<>();
        Map<Integer, Integer> secondaMappaDelleOccorrenze = new HashMap<>();

        for (int[] riga : primoGenitore)
            for (int id : riga)
                primaMappaDelleOccorrenze.put(id, primaMappaDelleOccorrenze.getOrDefault(id, 0) + 1);

        for (int[] riga : secondoGenitore)
            for (int id : riga)
                secondaMappaDelleOccorrenze.put(id, secondaMappaDelleOccorrenze.getOrDefault(id, 0) + 1);

        System.out.println(primaMappaDelleOccorrenze);

        for(int riga = 0; riga < altezza; riga++) {
            for(int colonna = 0; colonna < larghezza; colonna++) {

                boolean numeroMassimoPrimoGene = false;
                boolean numeroMassimoSecondoGene = false;

                int primoGene = primoGenitore[riga][colonna];
                int secondoGene = secondoGenitore[riga][colonna];

                double rand = random.nextDouble();

                if (primaMappaDelleOccorrenze.get(primoGene) == 0)
                    numeroMassimoPrimoGene = true;
                if (primaMappaDelleOccorrenze.get(secondoGene) == 0)
                    numeroMassimoSecondoGene = true;

                if(rand > 0.5) {

                    if(numeroMassimoPrimoGene) {

                        primoFiglio[riga][colonna] = secondoGene;
                        secondoFiglio[riga][colonna] = primoGene;
                        primaMappaDelleOccorrenze.replace(secondoGene,primaMappaDelleOccorrenze.get(secondoGene) - 1);
                        secondaMappaDelleOccorrenze.replace(primoGene,secondaMappaDelleOccorrenze.get(primoGene) - 1);

                    } else {

                        primoFiglio[riga][colonna] = primoGene;
                        secondoFiglio[riga][colonna] = secondoGene;
                        primaMappaDelleOccorrenze.replace(primoGene,primaMappaDelleOccorrenze.get(primoGene) - 1);
                        secondaMappaDelleOccorrenze.replace(secondoGene,secondaMappaDelleOccorrenze.get(secondoGene) - 1);

                    }

                } else {

                    if(numeroMassimoSecondoGene) {

                        primoFiglio[riga][colonna] = primoGene;
                        secondoFiglio[riga][colonna] = secondoGene;
                        primaMappaDelleOccorrenze.replace(primoGene,primaMappaDelleOccorrenze.get(primoGene) - 1);
                        secondaMappaDelleOccorrenze.replace(secondoGene,secondaMappaDelleOccorrenze.get(secondoGene) - 1);

                    } else {

                        primoFiglio[riga][colonna] = secondoGene;
                        secondoFiglio[riga][colonna] = primoGene;
                        primaMappaDelleOccorrenze.replace(secondoGene,primaMappaDelleOccorrenze.get(secondoGene) - 1);
                        secondaMappaDelleOccorrenze.replace(primoGene,secondaMappaDelleOccorrenze.get(primoGene) - 1);

                    }
                }
            }
        }

        IndividuoEntity primoFiglioIndividuo = new IndividuoEntity();
        IndividuoEntity secondoFiglioIndividuo = new IndividuoEntity();

        primoFiglioIndividuo.setAreaSelezionata(primoFiglio);
        secondoFiglioIndividuo.setAreaSelezionata(secondoFiglio);

        if(!primoFiglioIndividuo.isValid(primaMappaDelleOccorrenze))
            primoFiglioIndividuo.recovery(primaMappaDelleOccorrenze);

        if(!secondoFiglioIndividuo.isValid(secondaMappaDelleOccorrenze))
            secondoFiglioIndividuo.recovery(secondaMappaDelleOccorrenze);

        figli.add(primoFiglioIndividuo);
        figli.add(secondoFiglioIndividuo);

        return figli;
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
