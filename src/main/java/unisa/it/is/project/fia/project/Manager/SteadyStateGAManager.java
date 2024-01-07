package unisa.it.is.project.fia.project.Manager;

import unisa.it.is.project.fia.project.Entity.PopolazioneEntity;

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
}
