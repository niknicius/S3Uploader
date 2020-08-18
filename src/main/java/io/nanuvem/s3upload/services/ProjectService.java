package io.nanuvem.s3upload.services;

import io.nanuvem.s3upload.models.Application;
import io.nanuvem.s3upload.utils.FileHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProjectService {

    public Application deploy(Application application){
        try {
            List<Path> a = FileHandler.walk(Paths.get(application.getLocation()));
            for (Path p: a) {
                System.out.println(p.normalize().toString().replace(application.getLocation(), ""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return application;
    }

}
