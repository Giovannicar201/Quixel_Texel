package QuixelTexel.IS.Service.GEN.GEN;

import QuixelTexel.IS.Entity.GEN.GEN.EntitaEntity;
import QuixelTexel.IS.Exception.GEN.GEN.*;
import jakarta.persistence.EntityNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface EntitaService {

    void creaEntita(String email, String nomeImmagine, String nome, String collisioni, String nomeCartella, List<String> nomiProprieta, List<String> valoriProprieta)
            throws InvalidCollisionException, FolderNotFoundException, InvalidNumberOfPropertyException, NotUniqueEntityException, InvalidEntityNameException, ImageNotFoundException, ImageAlreadyUsedException;

    String visualizzaEntita(String nome, String email)
            throws EntityNotFoundException, QuixelTexel.IS.Exception.GEN.GEN.EntityNotFoundException;

    String visualizzaListaEntitaInCartella(String email, String nomeCartella)
            throws SQLException;

    String visualizzaListaEntita(String email)
            throws SQLException;

    EntitaEntity get(String nome, String email);

    EntitaEntity get(int id);

}
