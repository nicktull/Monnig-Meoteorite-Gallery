package edu.tcu.cs.monning_meteorite_gallery.System.exception;

public class ObjectNotFoundException extends RuntimeException {
    //Meteorites has string for pid
    public ObjectNotFoundException(String objectName, String id) {
        super("Could not find " + objectName + " with id " + id);
    }

    //loans has integer for pid
    public ObjectNotFoundException(String objectName, Integer id) {
        super("Could not find " + objectName + " with id " + id);
    }

}
