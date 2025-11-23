package com.zsgs.filesystem.model;

public interface Folder extends Cloneable {

    String getName();
    String getPath();
    int getSize();
    String toJson();
    Folder clone();
}
