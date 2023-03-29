package me.ikapkova.recipesbook.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import java.nio.file.StandardCopyOption;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;
import java.io.FileNotFoundException;
import me.ikapkova.recipesbook.model.Recipe;
import org.springframework.core.io.InputStreamResource;
@Service
public class FileService {
    private final Path filesDir;
    private final ObjectMapper objectMapper;

    public FileService(ObjectMapper objectMapper, @Value("${app.files.dir}") Path filesDir) {
        this.objectMapper = objectMapper;
        this.filesDir = filesDir;
    }

    public <T> void saveToFile(String fileName, T objectToSave)  {
       try {
           String json = objectMapper.writeValueAsString(objectToSave);
           Files.createDirectories(filesDir);
           Path filePath = filesDir.resolve(fileName + ".json");
           Files.deleteIfExists(filePath);
           Files.createFile(filePath);
           Files.writeString(filePath, json);
       }
       catch (IOException e){
           e.printStackTrace();
       }
    }

    public <T> T readFromFile(String fileName, TypeReference<T> typeReference) {
        Path filePath = filesDir.resolve(fileName + ".json");
        if (!Files.exists(filePath)) {
            return null;
        }
        try {
            String jsonString= Files.readString(filePath);
            T obj = objectMapper.readValue (jsonString, typeReference);
            return obj;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    public Resource getRecource(String fileName) {
       Path filePath = filesDir.resolve(fileName+".json");
       return new FileSystemResource(filePath);
    }
    public void saveRecource(String fileName, Resource resource){
        Path filePath = filesDir.resolve(fileName + ".json");
        try {
            Files.copy(resource.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//
    public Resource getRecourceTxt(String fileName) {
        Path filePath = filesDir.resolve(fileName+".txt");
        return new FileSystemResource(filePath);
    }
}
