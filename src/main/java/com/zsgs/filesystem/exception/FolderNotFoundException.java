package com.zsgs.filesystem.exception;

public class FolderNotFoundException extends RuntimeException{

    public FolderNotFoundException(String message){
        super(message);
    }
}
