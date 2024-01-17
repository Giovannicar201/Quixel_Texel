package QuixelTexel.IS.Entity.GEN.GEN;

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
@Table(name = "proprieta")
public class ProprietaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProprieta")
    private int id;
    private String nome;
    private String valore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntita",referencedColumnName = "idEntita")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EntitaEntity entitaEntity;
}
