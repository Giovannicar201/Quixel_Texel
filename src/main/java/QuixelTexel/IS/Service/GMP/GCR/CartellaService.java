package QuixelTexel.IS.Service.GMP.GCR;


import QuixelTexel.IS.Entity.GMP.GCR.CartellaEntity;
import QuixelTexel.IS.Exception.GMP.GCR.InvalidFolderNameException;
import QuixelTexel.IS.Exception.GMP.GCR.NotUniqueFolderException;

public interface CartellaService {
    void creaCartella(String nome, String email) throws InvalidFolderNameException, NotUniqueFolderException;

    String visualizzaListaCartelle(String email);

    CartellaEntity get(String nome, String email);

    CartellaEntity get(int id);
}
