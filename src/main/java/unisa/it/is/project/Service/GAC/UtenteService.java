package unisa.it.is.project.Service.GAC;

import org.json.simple.parser.ParseException;
import unisa.it.is.project.Entity.GAC.UtenteEntity;
import unisa.it.is.project.Exception.GAC.*;
import java.security.NoSuchAlgorithmException;

public interface UtenteService {
    void signup(String email, String nome, String password, String passwordRipetuta) throws NoSuchAlgorithmException, InvalidNameException, InvalidEmailException, InvalidPasswordException, SignupPasswordsMismatchException, NotUniqueUserException;

    void login(String email, String password) throws NoSuchAlgorithmException, UserNotFoundException, LoginPasswordsMismatchException, ParseException;

    UtenteEntity get(String email);
}
