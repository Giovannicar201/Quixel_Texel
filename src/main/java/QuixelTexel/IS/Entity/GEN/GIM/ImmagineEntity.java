package QuixelTexel.IS.Entity.GEN.GIM;

import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import QuixelTexel.IS.Entity.GAC.UtenteEntity;

import java.sql.Blob;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "immagine")
public class ImmagineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFoto")
    private int id;
    private String nome;
    @Lob
    private Blob immagine;

    @OneToOne(mappedBy = "immagineEntity",cascade = CascadeType.REMOVE)
    private EntitaEntity entitaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email",referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UtenteEntity utenteEntity;
}
