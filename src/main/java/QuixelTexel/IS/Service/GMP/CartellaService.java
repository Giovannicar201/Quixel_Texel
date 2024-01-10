package QuixelTexel.IS.Service.GMP;


import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import QuixelTexel.IS.Exception.GMP.InvalidFolderNameException;
import QuixelTexel.IS.Exception.GMP.NotUniqueFolderException;

public interface CartellaService {
    void creaCartella(String nome, String email) throws InvalidFolderNameException, NotUniqueFolderException;

    String visualizzaListaCartelle(String email);

    CartellaEntity get(String nome, String email);

    CartellaEntity get(int id);
}
