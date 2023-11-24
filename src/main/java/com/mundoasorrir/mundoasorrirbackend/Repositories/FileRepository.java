package com.mundoasorrir.mundoasorrirbackend.Repositories;

import com.mundoasorrir.mundoasorrirbackend.Domain.File.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByUsersAllowed_Username(String userName);
}
