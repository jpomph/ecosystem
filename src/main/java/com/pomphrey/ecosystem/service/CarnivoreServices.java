package com.pomphrey.ecosystem.service;

import com.pomphrey.ecosystem.exception.DataIntegrityException;
import com.pomphrey.ecosystem.model.configuration.Species;
import org.springframework.stereotype.Service;

@Service
public class CarnivoreServices {

    public void checkDataIntegrity(Species species) throws DataIntegrityException {

        return;
    }

}
