package com.zsgs.filesystem.persistance;



import com.zsgs.filesystem.model.FolderNode;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileSystemSerializer {

    private final String filePath;

    public FileSystemSerializer() {
        filePath = "D:\\llds\\folderManagement\\untitled\\src\\json.txt";
    }

    public void saveToFile(FolderNode rootNode) throws FileNotFoundException {
        System.out.println("Saving file system to " + filePath + "...");
        String jsonString = rootNode.toJson();

        try (FileWriter fileWriter = new FileWriter(filePath)) {

            fileWriter.write(jsonString);
            System.out.println(" Json file saved  Successful!");

        } catch (IOException e) {

            System.err.println(" Error saving file: " + e.getMessage());

        }
    }


}