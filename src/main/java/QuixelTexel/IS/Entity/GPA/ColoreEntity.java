package QuixelTexel.IS.Entity.GPA;

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
@Table(name = "colore")
public class ColoreEntity {

    @Id
    @Column(name = "idColore")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idColore;

    private String esadecimale;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "idPalette",referencedColumnName = "idPalette")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PaletteEntity paletteEntity;
}
