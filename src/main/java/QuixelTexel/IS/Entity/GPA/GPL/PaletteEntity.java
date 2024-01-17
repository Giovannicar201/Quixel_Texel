package QuixelTexel.IS.Entity.GPA.GPL;

import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Palette")
public class PaletteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPalette")
    private int idPalette;
    private String nomePalette;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email",referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UtenteEntity utenteEntity;

    @OneToMany(mappedBy = "paletteEntity",cascade = CascadeType.REMOVE)
    private List<ColoreEntity> coloreEntityList;
}
