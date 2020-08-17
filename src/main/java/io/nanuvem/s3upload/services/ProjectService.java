package io.nanuvem.s3upload.services;

import io.nanuvem.s3upload.models.Application;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    public Application deploy(Application application){
        return application;
    }

}
