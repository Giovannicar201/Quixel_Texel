package QuixelTexel.IS.Entity.GEV;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "evento")
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEvento")
    private int idEvento;
    private String nome;
    private String riga;
    private String colonna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMappa",referencedColumnName = "idMappa")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MappaEntity mappaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UtenteEntity utenteEntity;

    @OneToMany(mappedBy = "eventoEntity",cascade = CascadeType.ALL)
    private List<IstruzioneEntity> istruzioneEntityList;
}
