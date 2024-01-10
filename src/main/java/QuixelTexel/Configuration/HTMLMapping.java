package QuixelTexel.Configuration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import QuixelTexel.IS.Entity.GAC.UtenteEntity;

@Controller

public class HTMLMapping {

    @GetMapping("/auth")

    public String loginRegistrazione(Model model){

        model.addAttribute("infoUtente", new UtenteEntity());

        return "autenticazione";

    }

}
