package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import com.mundoasorrir.mundoasorrirbackend.Repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

//@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {
    @Autowired
    private final FileRepository fileRepository;


    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File FileDB = new File(fileName, file.getContentType(), file.getBytes());

        return fileRepository.save(FileDB);
    }

    public File getFile(Long id) {
        return fileRepository.findById(id).get();
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }


}
