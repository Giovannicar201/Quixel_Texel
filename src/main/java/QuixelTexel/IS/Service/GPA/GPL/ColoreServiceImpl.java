package QuixelTexel.IS.Service.GPA.GPL;

import QuixelTexel.IS.Entity.GPA.ColoreEntity;
import QuixelTexel.IS.Entity.GPA.PaletteEntity;
import QuixelTexel.IS.Repository.GPA.ColoreRepository;
import jakarta.transaction.Transactional;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ColoreServiceImpl implements ColoreService {

    @Autowired
    private ColoreRepository coloreRepository;

    @Override
    @Transactional
    public void creaColore(String esadecimale, PaletteEntity paletteEntity) {

        ColoreEntity coloreEntity = new ColoreEntity();
        coloreEntity.setEsadecimale(esadecimale);

        coloreEntity.setPaletteEntity(paletteEntity);

        coloreRepository.save(coloreEntity);
    }

    @Override
    @Transactional
    public ColoreEntity get(int id) {

        ColoreEntity coloreEntity = coloreRepository.findById(id).orElse(null);

        return coloreEntity;
    }

    @Override
    @Transactional
    public void save(ColoreEntity coloreEntity){
        coloreRepository.save(coloreEntity);
    }

    @Override
    @Transactional
    public String visualizzaListaColoriInPalette(PaletteEntity paletteEntity){

        JSONObject coloriJSON = new JSONObject();
        JSONArray coloriArray = new JSONArray();

        List<ColoreEntity> colori = coloreRepository.findAllByPaletteEntity(paletteEntity);

        for(ColoreEntity coloreEntity : colori){
            coloriArray.add(coloreEntity.getEsadecimale());
        }

        coloriJSON.put("colori", coloriArray);

        return coloriJSON.toString();
    }
}

