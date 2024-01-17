package QuixelTexel.IS.Entity.GEV;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "istruzione")
public class IstruzioneEntity {

    @Id
    @Column(name = "idIstruzione")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idIstruzione;

    private String nome;
    private String valore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEvento",referencedColumnName = "idEvento")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EventoEntity eventoEntity;
}
