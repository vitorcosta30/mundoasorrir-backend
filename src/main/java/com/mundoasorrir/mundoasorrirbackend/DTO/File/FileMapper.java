package com.mundoasorrir.mundoasorrirbackend.DTO.File;

import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

public class FileMapper {


    public static List<FileDTO> toDTO(List<File> files){
        List<FileDTO> res = new ArrayList<>();
        for( int i = 0; i < files.size(); i++){
            res.add(toDTO(files.get(i)));
        }
        return res;
    }
    public static FileDTO toDTO(File file){
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/files/files/")
                .path(file.getId().toString())
                .toUriString();
        if(file.getSharedBy() != null){
            return new FileDTO(file.getName(),fileDownloadUri,file.getType(),file.getData().length,file.getSharedBy().getUsername());

        }else{
            return new FileDTO(file.getName(),fileDownloadUri,file.getType(),file.getData().length,"");
        }
    }
}
