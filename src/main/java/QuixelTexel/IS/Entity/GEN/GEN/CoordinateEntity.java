package QuixelTexel.IS.Entity.GEN.GEN;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "coordinate")
public class CoordinateEntity {
    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrimaryKeyCoordinate implements Serializable {
        @Column(name = "coordinataX")
        private String coordinataX;
        @Column(name = "coordinataY")
        private String coordinataY;
        @Column(name = "idEntita")
        private int idEntita;
    }

    @EmbeddedId
    private PrimaryKeyCoordinate primaryKeyCoordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEntita",referencedColumnName = "idEntita",insertable=false, updatable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EntitaEntity entitaEntity;
}
