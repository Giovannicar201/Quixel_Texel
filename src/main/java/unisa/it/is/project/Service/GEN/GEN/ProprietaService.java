package unisa.it.is.project.Service.GEN.GEN;

import unisa.it.is.project.Entity.GEN.GEN.EntitaEntity;
import unisa.it.is.project.Entity.GEN.GEN.ProprietaEntity;
import unisa.it.is.project.Exception.GEN.GEN.InvalidPropertyNameException;
import unisa.it.is.project.Exception.GEN.GEN.InvalidPropertyValueException;
import unisa.it.is.project.Exception.GEN.GEN.NotUniquePropertyException;
import unisa.it.is.project.Exception.GEN.GEN.PropertyNotFoundException;

import java.util.List;

public interface ProprietaService {
    void creaProprieta(String nomeProprieta, String valoreProprieta, EntitaEntity entita) throws InvalidPropertyNameException, InvalidPropertyValueException, NotUniquePropertyException;

    void modificaProprieta(String nomeProprieta, String valoreProprieta, EntitaEntity entita) throws PropertyNotFoundException, InvalidPropertyNameException, InvalidPropertyValueException, NotUniquePropertyException;

    List<ProprietaEntity> getLista(EntitaEntity entita);

    void save(ProprietaEntity proprieta);
}
