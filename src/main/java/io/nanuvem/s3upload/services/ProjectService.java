package io.nanuvem.s3upload.services;

import io.nanuvem.s3upload.models.Application;
import io.nanuvem.s3upload.utils.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private DeployService deployService;

    public Application deploy(Application application){
        try {
            List<Path> paths = FileHandler.walk(Paths.get(application.getLocation()));
            deployService.deploy(application, paths);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return application;
    }

}
