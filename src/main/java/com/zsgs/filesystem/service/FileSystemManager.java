package com.zsgs.filesystem.service;

import com.zsgs.filesystem.exception.FolderNotFoundException;
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

    private final FolderNode root ;
    @Setter
    private FolderNode currentDir ;
    private static FileSystemManager instance ;
    private final ClipBoardManager clipboard = ClipBoardManager.getInstance();
    private final FileSystemSerializer serializer = new  FileSystemSerializer();

    private FileSystemManager(){

        this.root = new FolderNode();
        this.root.setName("root");
        this.root.setParentNode(null);
        setCurrentDir(root);
    }


    public static synchronized FileSystemManager getInstance(){
        if( instance == null){
            instance = new FileSystemManager();
        }
        return instance ;
    }

    public String getCurrentPath(){
        return currentDir.getPath();
    }
    public void getFolderSize(){
        int size = currentDir.getSize();
        System.out.println("folder size Including folders & files " + size);
    }

    public void add(FolderNode folder){
        currentDir.addItem(folder);
    }

    public void remove(String name){
        currentDir.remove(name);
    }
    public  void copy(String name){

        if (name == null || name.isEmpty()){
            throw new InvalidCmtException("please mention name of the item to copy \n ---> Eg : copy filename");
        }

        Optional<Folder> item = currentDir.getChildren().stream()
                .filter(c->c.getName().equals(name))
                .findFirst();
        if( item.isPresent()) {
            Folder folder = item.get().clone();
            clipboard.copy(folder);

        }
        else {
            throw  new ItemNotFoundException("Item "+name+ " Not found in Current Directory");
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
                System.out.println("  Directory Changed to " +open.getName() +" folder");
                setCurrentDir((FolderNode) open);
            }
            else {
                throw new InvalidCmtException(" Invalid comment there no such  Folder Component ");
            }
        }
        else {
            throw new FolderNotFoundException("folder " + name + " Not present in current Directory");
        }
    }

    public void listContents (){
        List<Folder> folders = currentDir.getChildren();
        if ( folders.isEmpty()){
            System.out.println(" Current Directory is Empty ");
        }
        else {

            for ( Folder f : folders){
                System.out.printf("%-10s\n",f.getName());
            }
        }

    }

    public void createFolder(String name){

        if ( name.isEmpty() ){
            throw  new InvalidCmtException(" create Folder with name");
        }
        else {

            boolean exists = currentDir.getChildren().stream()
                    .anyMatch(node -> node.getName().equals(name));

            if (exists) {
                throw new InvalidCmtException("Error: A file or folder named '" + name + "' already exists.");
            }
            FolderNode folder =new FolderNode() ;
            folder.setName(name);
            folder.setParentNode(currentDir);
            currentDir.addItem(folder);
        }
    }
    public void createFile(String name){
        File file = new File();

        if ( name == null || name.isEmpty()) {
            throw new InvalidCmtException(" Give name for file ");
        }
        else {
            file.setName(name);
            file.setParentNode(currentDir);
            currentDir.addItem(file);
        }
    }
    public void saveSystem(){
        serializer.saveToFile(this.root);
    }

    public void goBack(){

        if (currentDir.getParentNode() == null){
            throw new InvalidCmtException(" You Already reached root node");
        }
        FolderNode folder = (FolderNode) currentDir.getParentNode();
        System.out.println("  Directory Changed to " +folder.getName() +"folder");
        setCurrentDir(folder);

    }
    public void goToRoot(){

        if( currentDir == root){
            throw new InvalidCmtException("you are Already in root Node ");
        }
        setCurrentDir(this.root);
    }



}
