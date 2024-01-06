package unisa.it.is.project.fia.project.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EntitaPiazzataEntity {

    private int id;
    private int riga;
    private int colonna;

    @Override
    public String toString() {

        return "@entita " +
                "@id::" + id + " " +
                "@colonna::" + colonna + " " +
                "@riga::" + riga;
    }

    @Override
    public EntitaPiazzataEntity clone() throws CloneNotSupportedException {

        EntitaPiazzataEntity clone = (EntitaPiazzataEntity) super.clone();

        clone.setId(id);
        clone.setColonna(colonna);
        clone.setRiga(riga);

        return clone;
    }
}
