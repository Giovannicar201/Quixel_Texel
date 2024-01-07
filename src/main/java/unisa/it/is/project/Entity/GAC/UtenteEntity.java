package unisa.it.is.project.Entity.GAC;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unisa.it.is.project.Entity.GEN.GEN.EntitaEntity;
import unisa.it.is.project.Entity.GEN.GIM.ImmagineEntity;
import unisa.it.is.project.Entity.GMP.GCR.CartellaEntity;
import unisa.it.is.project.Entity.GMP.GMP.MappaEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="utente")
public class UtenteEntity {
    @Id
    @Column(name = "email")
    private String email;
    private String password;
    private String nome;

    @OneToOne(mappedBy = "utenteEntity",cascade = CascadeType.REMOVE)
    private MappaEntity mappaEntity;

    @OneToMany(mappedBy = "utenteEntity", cascade = CascadeType.REMOVE)
    private List<CartellaEntity> cartellaEntityList;

    @OneToMany(mappedBy = "utenteEntity", cascade = CascadeType.REMOVE)
    private List<ImmagineEntity> immagineEntityList;

    @OneToMany(mappedBy = "utenteEntity",cascade = CascadeType.REMOVE)
    private List<EntitaEntity> entitaEntityList;
}
