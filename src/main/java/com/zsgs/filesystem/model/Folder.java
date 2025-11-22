package com.zsgs.filesystem.model;

public interface Folder extends Cloneable {

    public String getName();
    public String getPath();
    public int getSize();
    public String toJson();
    public Folder clone();
}
