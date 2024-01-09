package unisa.it.is.project.Service.GEN.GIM;

import org.springframework.web.multipart.MultipartFile;
import unisa.it.is.project.Entity.GEN.GIM.ImmagineEntity;
import unisa.it.is.project.Exception.GEN.GIM.InvalidFileSizeException;
import unisa.it.is.project.Exception.GEN.GIM.NotUniqueImageException;

import java.io.IOException;
import java.sql.SQLException;

public interface ImmagineService {

    void caricaImmagine(MultipartFile immagine, String email)
            throws SQLException, IOException, InvalidFileSizeException, NotUniqueImageException, InvalidFileSizeException, NotUniqueImageException;

    void integraPixelArt(MultipartFile immagine, String nomeFoto, String email);

    ImmagineEntity get(String nome, String email);

    String visualizzaListaImmagini(String email)
            throws SQLException;
}
