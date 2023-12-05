package com.mundoasorrir.mundoasorrirbackend.DTO.UploadInfo;

import org.springframework.util.unit.DataSize;

public class UploadInfoMapper {

    public static UploadInfoDTO toDTO(String maxSizeString){
        return new UploadInfoDTO(maxSizeString, DataSize.parse(maxSizeString).toBytes());
    }
}
