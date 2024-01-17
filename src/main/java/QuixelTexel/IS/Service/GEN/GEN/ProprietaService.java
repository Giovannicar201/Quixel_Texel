package QuixelTexel.IS.Service.GEN.GEN;

import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Entity.GEN.GEN.ProprietaEntity;
import QuixelTexel.IS.Exception.GEN.GEN.InvalidPropertyNameException;
import QuixelTexel.IS.Exception.GEN.GEN.InvalidPropertyValueException;
import QuixelTexel.IS.Exception.GEN.GEN.NotUniquePropertyException;
import java.util.List;

public interface ProprietaService {

    /**
     * Crea una nuova proprietà associata a un'entità.
     *
     * @param nomeProprieta Il nome della proprietà.
     * @param valoreProprieta Il valore della proprietà.
     * @param entita L'entità a cui associare la proprietà.
     * @throws InvalidPropertyNameException Se il nome della proprietà non è valido.
     * @throws InvalidPropertyValueException Se il valore della proprietà non è valido.
     * @throws NotUniquePropertyException Se la proprietà è già presente nell'entità.
     */
    void creaProprieta(String nomeProprieta, String valoreProprieta, EntitaEntity entita)
            throws InvalidPropertyNameException,
            InvalidPropertyValueException,
            NotUniquePropertyException;

    /**
     * Ottiene la lista delle proprietà associate a un'entità.
     *
     * @param entita L'entità di cui ottenere la lista di proprietà.
     * @return La lista di proprietà associate all'entità.
     */
    List<ProprietaEntity> getLista(EntitaEntity entita);

    /**
     * Salva una proprietà nel database.
     *
     * @param proprieta La proprietà da salvare.
     */
    void save(ProprietaEntity proprieta);
}
