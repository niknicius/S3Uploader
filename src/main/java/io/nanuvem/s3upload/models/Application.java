package io.nanuvem.s3upload.models;

public class Application {

    private String location;
    private String name;
    private String handler;

    public Application(String location, String name, String handler) {
        this.location = location;
        this.name = name;
        this.handler = handler;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
