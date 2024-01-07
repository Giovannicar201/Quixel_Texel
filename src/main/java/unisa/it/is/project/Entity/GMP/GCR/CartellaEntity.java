package unisa.it.is.project.Entity.GMP.GCR;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import unisa.it.is.project.Entity.GAC.UtenteEntity;
import unisa.it.is.project.Entity.GEN.GEN.EntitaEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cartella")
public class CartellaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCartella")
    private int id;
    private String nome;

    @OneToMany(mappedBy = "cartellaEntity",cascade = CascadeType.REMOVE)
    private List<EntitaEntity> entitaEntityList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email",referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UtenteEntity utenteEntity;
}