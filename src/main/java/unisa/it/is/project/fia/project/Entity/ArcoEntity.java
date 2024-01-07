package unisa.it.is.project.fia.project.Entity;

import lombok.Getter;

@Getter
public class ArcoEntity {

    private final String v;
    private final String w;
    private final double peso;

    /**
     * Crea un arco.
     *
     * @param v Stringa rappresentativa del vertice di partenza.
     * @param w Stringa rappresentativa del vertice di arrivo.
     * @author Giovanni Carbone
     */
    public ArcoEntity(EntitaPiazzataEntity v, EntitaPiazzataEntity w) {
        this.v = v.toString();
        this.w = w.toString();
        this.peso = Math.sqrt(Math.pow((v.getRiga() - w.getRiga()),2) + Math.pow((v.getColonna() - w.getColonna()),2));
    }
}
