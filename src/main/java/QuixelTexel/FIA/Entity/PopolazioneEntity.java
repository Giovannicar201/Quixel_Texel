package QuixelTexel.FIA.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class PopolazioneEntity {

    private final List<IndividuoEntity> popolazione = new ArrayList<>();

    public void aggiungiIndividuo(IndividuoEntity individuo) {
        popolazione.add(individuo);
    }

    @Override
    public String toString() {

        StringBuilder risultato = new StringBuilder("@log\n\n");

        for (IndividuoEntity individuo : popolazione)
            risultato.append(individuo.toString()).append("\n");

        return risultato.toString();
    }
}
