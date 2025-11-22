package com.zsgs.filesystem.service;

import com.zsgs.filesystem.exception.InvalidCmtException;
import com.zsgs.filesystem.exception.ItemNotFoundException;
import com.zsgs.filesystem.model.File;
import com.zsgs.filesystem.model.Folder;
import com.zsgs.filesystem.model.FolderNode;
import com.zsgs.filesystem.persistance.FileSystemSerializer;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

public class FileSystemManager {

    private FolderNode root ;
    @Setter
    private FolderNode currentDir ;
    private static FileSystemManager instance ;
    private ClipBoardManager clipboard ;
    private FileSystemSerializer serializer ;

    private FileSystemManager(){
        setUp();
    }

    void setUp(){
        FolderNode root = new FolderNode();
        root.setName("root");
        root.setParentNode(null);
        clipboard = ClipBoardManager.getInstance();
        serializer = new FileSystemSerializer();
    }

    public static FileSystemManager getInstance(){
        if( instance == null){
            instance = new FileSystemManager();
        }
        return instance ;
    }

    public String getCurrentPath(){
        return currentDir.getPath();
    }
    public int getFolderSize(){
        return currentDir.getSize();
    }

    public void add(FolderNode folder){
        currentDir.addItem(folder);
    }

    public void remove(String name){
        currentDir.remove(name);
    }
    public  void copy(String name){

        Optional<Folder> item = currentDir.getChildren().stream()
                .filter(c->c.getName().equals(name))
                .findFirst();
        if( item.isPresent()) {
            Folder folder = item.get().clone();
            clipboard.copy(folder);

        }
        else {
            throw  new ItemNotFoundException("Item "+name+ " Not found in Cuurent Directory");
        }
    }

    public void paste(){
        List<Folder> items = clipboard.paste();

        if (items.isEmpty()){

            throw new ItemNotFoundException(" No items in the clipBoard");
        }
        for (Folder folder : items){
            currentDir.addItem(folder);
        }
    }
    public void changeDirectory(String name){

        if ( name.equals("..")){
            goBack();
            return ;
        }
        Optional<Folder> folder = currentDir.getChildren().stream()
                .filter(f-> f.getName().equals(name))
                .findFirst();

        if (folder.isPresent()){
            Folder open = folder.get();
            if ( open instanceof FolderNode){
                setCurrentDir((FolderNode) open);
            }
            else {
                throw new InvalidCmtException(" No Folder Component ");
            }
        }
        else {
            throw new ItemNotFoundException("folder " + name + " Not present in current Directory");
        }
    }

    public void listContents (){
        List<Folder> folders = currentDir.getChildren();
        if ( folders.isEmpty()){
            System.out.println(" Cuurent Directory is Empty ");
        }
        else {

            for ( Folder f : folders){
                System.out.printf("%-10s\n",f.getName());
            }
        }

    }

    public void createFolder(String name){
        FolderNode folder =new FolderNode() ;
        if ( name == ""){
            throw  new InvalidCmtException(" create Folder with name");

        } else {
            folder.setName(name);
            folder.setParentNode(currentDir);
            currentDir.addItem(folder);
        }
    }
    public void createFile(String name){
        File file = new File();

        if ( name == null || name == "") {
            throw new InvalidCmtException(" Give name for file ");
        }
        else {
            file.setName(name);
            file.setParentNode(currentDir);
            currentDir.addItem(file);
        }
    }
    public void saveSystem(){
        serializer.saveToFile(currentDir);
    }

    public void goBack(){
        FolderNode folder = (FolderNode) currentDir.getParentNode();
        System.out.println("  Directory Changed to folder " +folder.getName());
        setCurrentDir(folder);

    }
    public void goToRoot(){
        setCurrentDir(this.root);
    }



}
