package com.zsgs.filesystem.service;

import com.zsgs.filesystem.model.Folder;

import java.util.ArrayList;
import java.util.List;

public class ClipBoardManager {

    private static ClipBoardManager instance ;
    private final List<Folder> clipboard = new ArrayList<>();

    private ClipBoardManager()
    {

    }
    // singleton method
    public static ClipBoardManager getInstance() {
        if ( instance == null){
            instance = new ClipBoardManager();
        }
        return instance;
    }

    public void  copy(Folder folder){
        clipboard.add(folder);
    }
    public List<Folder> paste () {
        List<Folder>  items = new ArrayList<>(clipboard);
        clipboard.clear();
        return items ;
    }
}
