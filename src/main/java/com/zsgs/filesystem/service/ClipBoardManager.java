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
    public static synchronized ClipBoardManager getInstance() {
        if ( instance == null){
            instance = new ClipBoardManager();
        }
        return instance;
    }

    public synchronized void   copy(Folder folder){

        clipboard.add(folder);
    }
    public synchronized List<Folder> paste () {
        List<Folder>  items = new ArrayList<>(clipboard);
        clipboard.clear();
        return items ;
    }
}
