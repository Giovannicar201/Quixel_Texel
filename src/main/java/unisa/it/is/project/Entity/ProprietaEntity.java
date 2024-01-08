package unisa.it.is.project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import unisa.it.is.project.Entity.GEN.GEN.EntitaEntity;

@Data
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
