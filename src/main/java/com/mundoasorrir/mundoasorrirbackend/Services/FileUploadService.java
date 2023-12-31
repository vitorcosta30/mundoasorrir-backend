package com.mundoasorrir.mundoasorrirbackend.Services;

import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import com.mundoasorrir.mundoasorrirbackend.Domain.User.SystemUser;
import com.mundoasorrir.mundoasorrirbackend.Repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

//@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {
    @Autowired
    private final FileRepository fileRepository;


    public File store(MultipartFile file, List<SystemUser> users, SystemUser creator) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileDB = new File(fileName, file.getContentType(), file.getBytes(), users);
        fileDB.setSharedBy(creator);

        return fileRepository.save(fileDB);
    }

    public File getFile(Long id) {
        return fileRepository.findById(id).get();
    }

    public Stream<File> getAllFiles(String userName) {
        return fileRepository.findByUsersAllowed_Username(userName).stream();
    }


}
