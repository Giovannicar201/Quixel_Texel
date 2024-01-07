package unisa.it.is.project.Entity.GEN.GEN;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unisa.it.is.project.Entity.GAC.UtenteEntity;
import unisa.it.is.project.Entity.GEN.GIM.ImmagineEntity;
import unisa.it.is.project.Entity.GMP.GCR.CartellaEntity;
import unisa.it.is.project.Entity.GMP.GMP.MappaEntity;

import java.util.List;

@Data
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