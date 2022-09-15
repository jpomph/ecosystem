package com.pomphrey.ecosystem.service;

import com.pomphrey.ecosystem.dao.SpeciesDao;
import com.pomphrey.ecosystem.exception.DataIntegrityException;
import com.pomphrey.ecosystem.model.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarnivoreServices {

    @Autowired
    SpeciesDao speciesDao;

    public void checkDataIntegrity(Species species) throws DataIntegrityException {

        //check that prey exists
        try {
            Species prey = speciesDao.querySingleSpecies(species.getFood());
        }
        catch(IllegalArgumentException ex){
            if(ex.getMessage().contains("Species Not Found")){
                throw new DataIntegrityException("Prey Not Found");
            }
        }
    }

}
