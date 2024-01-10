package QuixelTexel.IS.Service.GEN.GEN;

import QuixelTexel.IS.Exception.GEN.GEN.InvalidPropertyNameException;
import QuixelTexel.IS.Exception.GEN.GEN.InvalidPropertyValueException;
import QuixelTexel.IS.Exception.GEN.GEN.NotUniquePropertyException;
import QuixelTexel.IS.Exception.GEN.GEN.PropertyNotFoundException;
import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Entity.GEN.GEN.ProprietaEntity;

import java.util.List;

public interface ProprietaService {
    void creaProprieta(String nomeProprieta, String valoreProprieta, EntitaEntity entita) throws InvalidPropertyNameException, InvalidPropertyValueException, NotUniquePropertyException;

    void modificaProprieta(String nomeProprieta, String valoreProprieta, EntitaEntity entita) throws PropertyNotFoundException, InvalidPropertyNameException, InvalidPropertyValueException, NotUniquePropertyException;

    List<ProprietaEntity> getLista(EntitaEntity entita);

    void save(ProprietaEntity proprieta);
}
