package QuixelTexel.IS.Entity.GEN.GEN;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import QuixelTexel.IS.Entity.GEN.GIM.ImmagineEntity;
import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "entita")
public class EntitaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEntita")
    private int id;
    private String nome;
    private String collisione;

    @OneToMany(mappedBy = "entitaEntity",cascade = CascadeType.REMOVE)
    private List<ProprietaEntity> proprietaEntityList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMappa",referencedColumnName = "idMappa")
    private MappaEntity mappaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCartella",referencedColumnName = "idCartella")
    private CartellaEntity cartellaEntity;

    @ManyToOne(fetch = FetchType.LAZY,optional = true)
    @JoinColumn(name = "email",referencedColumnName = "email")
    private UtenteEntity utenteEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFoto",referencedColumnName = "idFoto")
    private ImmagineEntity immagineEntity;
}
