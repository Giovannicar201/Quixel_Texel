package QuixelTexel.IS.Entity.GAC;

import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Entity.GEN.GIM.ImmagineEntity;
import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import QuixelTexel.IS.Entity.GMP.GMP.MappaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
