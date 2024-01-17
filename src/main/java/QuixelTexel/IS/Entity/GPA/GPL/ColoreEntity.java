package QuixelTexel.IS.Entity.GPA.GPL;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
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
    @JoinColumn(name = "idPalette",referencedColumnName = "idPalette",insertable=false, updatable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PaletteEntity paletteEntity;
}
