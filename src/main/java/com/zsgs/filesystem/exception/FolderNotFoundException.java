package com.zsgs.filesystem.exception;

public class FolderNotFoundException extends RuntimeException{

    FolderNotFoundException(String message){
        super(message);
    }
}
