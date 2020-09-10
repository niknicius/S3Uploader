package io.nanuvem.s3upload.utils;

public class NormalizeStrings {

    public static String normalizeS3ObjectKey(String key){
        String newKey = key;
        newKey = newKey.replace('\\', '/');
        if(newKey.startsWith("/")){
            newKey = newKey.replaceFirst("/", "");
        }
        return newKey;
    }

}
