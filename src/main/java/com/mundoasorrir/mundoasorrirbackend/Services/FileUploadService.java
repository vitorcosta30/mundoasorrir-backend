package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import com.mundoasorrir.mundoasorrirbackend.Repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
//@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileRepository fileRepository;

    public void saveFileInDatabase(MultipartFile file) throws IOException {
        File newFile = new File(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        fileRepository.save(newFile);
    }



}
