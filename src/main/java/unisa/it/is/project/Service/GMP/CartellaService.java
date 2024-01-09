package unisa.it.is.project.Service.GMP;


import unisa.it.is.project.Entity.GMP.GCR.CartellaEntity;
import unisa.it.is.project.Exception.GMP.InvalidFolderNameException;
import unisa.it.is.project.Exception.GMP.NotUniqueFolderException;

public interface CartellaService {
    void creaCartella(String nome, String email) throws InvalidFolderNameException, NotUniqueFolderException;

    String visualizzaListaCartelle(String email);

    CartellaEntity get(String nome, String email);

    CartellaEntity get(int id);
}
