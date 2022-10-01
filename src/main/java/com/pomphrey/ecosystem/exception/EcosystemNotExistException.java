package com.pomphrey.ecosystem.exception;

import com.pomphrey.ecosystem.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;

public class EcosystemNotExistException extends Exception{

    @Autowired
    Constants constants;

    String message;

    public EcosystemNotExistException(){
//        this.message = constants.ECOSYSTEM_NOT_EXIST;
    }

    @Override
    public String toString(){
        return constants.ECOSYSTEM_NOT_EXIST;
    }

}
