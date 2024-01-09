package unisa.it.is.project.Service.GEN.GIM;

import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import unisa.it.is.project.Entity.GAC.UtenteEntity;
import unisa.it.is.project.Entity.GEN.GIM.ImmagineEntity;
import unisa.it.is.project.Exception.GEN.GIM.InvalidFileSizeException;
import unisa.it.is.project.Exception.GEN.GIM.NotUniqueImageException;
import unisa.it.is.project.Repository.GEN.GIM.ImmagineRepository;
import unisa.it.is.project.Service.GAC.UtenteService;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Service
public class ImmagineServiceImpl
        implements ImmagineService {

    @Autowired
    private ImmagineRepository immagineRepository;
    @Autowired
    private UtenteService utenteService;

    @Override
    @Transactional
    public void caricaImmagine(MultipartFile immagine, String email)
            throws SQLException, IOException, InvalidFileSizeException, NotUniqueImageException {

        UtenteEntity utenteEntity = utenteService.get(email);

        ImmagineEntity immagineEntity = new ImmagineEntity();

        if(!isImageSizeValid(immagine))
            throw new InvalidFileSizeException("ERRORE - DIMENSIONE NON VALIDA.");

        Blob immagineBlob = convertMultipartFileToBlob(immagine);

        String nome = immagine.getOriginalFilename();

        ImmagineEntity immagineEntityQuery = immagineRepository.findByNomeAndUtenteEntity(nome,utenteEntity);

        if(immagineEntityQuery != null)
            throw new NotUniqueImageException("ERRORE - IMMAGINE GIÀ ESISTENTE.");

        immagineEntity.setImmagine(immagineBlob);
        immagineEntity.setNome(nome);
        immagineEntity.setUtenteEntity(utenteEntity);

        immagineRepository.save(immagineEntity);
    }

    private static Blob convertMultipartFileToBlob(MultipartFile multipartFile)
            throws SQLException, IOException {

        InputStream inputStream = multipartFile.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }

        byte[] bytes = byteArrayOutputStream.toByteArray();

        return new SerialBlob(bytes);
    }

    private static boolean isImageSizeValid(MultipartFile file)
            throws IOException {

        BufferedImage image = ImageIO.read(file.getInputStream());

        return image.getWidth() == 32 && image.getHeight() == 32;
    }

    @Override
    @Transactional
    public void integraPixelArt(MultipartFile immagine, String nome, String email) {
        /*TO-DO*/ //integrazione immagine
    }

    @Override
    @Transactional
    public ImmagineEntity get(String nome, String email) {

        UtenteEntity utenteEntity = utenteService.get(email);

        return immagineRepository.findByNomeAndUtenteEntity(nome,utenteEntity);
    }

    @Override
    public String visualizzaListaImmagini(String email)
            throws SQLException {

        UtenteEntity utenteEntity = utenteService.get(email);

        JSONObject immaginiJSON = new JSONObject();
        JSONArray blobImmagini = new JSONArray();

        List<ImmagineEntity> immaginiEntityQuery = immagineRepository.findAllByUtenteEntity(utenteEntity);

        for(ImmagineEntity immagineEntity : immaginiEntityQuery) {

            JSONObject immagineJSON = new JSONObject();

            Blob immagine = immagineEntity.getImmagine();
            byte[] bytes = immagine.getBytes(1, (int) immagine.length());

            immagineJSON.put(immagineEntity.getNome(), Base64.getEncoder().encodeToString(bytes));

            blobImmagini.add(immagineJSON);
        }

        immaginiJSON.put("blobImmagini", blobImmagini);

        return immaginiJSON.toString();
    }

}
