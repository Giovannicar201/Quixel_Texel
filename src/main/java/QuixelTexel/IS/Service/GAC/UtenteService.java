package QuixelTexel.IS.Service.GAC;

import QuixelTexel.IS.Exception.GAC.*;
import org.json.simple.parser.ParseException;
import QuixelTexel.IS.Entity.GAC.UtenteEntity;
import java.security.NoSuchAlgorithmException;

public interface UtenteService {
    void signup(String email, String nome, String password, String passwordRipetuta) throws NoSuchAlgorithmException, InvalidNameException, InvalidEmailException, InvalidPasswordException, SignupPasswordsMismatchException, NotUniqueUserException;

    void login(String email, String password) throws NoSuchAlgorithmException, UserNotFoundException, LoginPasswordsMismatchException, ParseException;

    UtenteEntity get(String email);
}
