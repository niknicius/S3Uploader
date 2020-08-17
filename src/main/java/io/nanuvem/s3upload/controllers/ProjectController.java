package io.nanuvem.s3upload.controllers;


import io.nanuvem.s3upload.models.Application;
import io.nanuvem.s3upload.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("projects")
@RequestMapping(path = "/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/")
    public Application createFunction(@RequestBody Application payload){
        return projectService.deploy(payload);
    }

}
