package com.mundoasorrir.mundoasorrirbackend.DTO.UploadInfo;

public class UploadInfoDTO {
    private String maxSizeString;

    private Long maxSizeBytes;

    public UploadInfoDTO(String maxSizeString, Long maxSizeBytes) {
        this.maxSizeString = maxSizeString;
        this.maxSizeBytes = maxSizeBytes;
    }

    public String getMaxSizeString() {
        return maxSizeString;
    }

    public void setMaxSizeString(String maxSizeString) {
        this.maxSizeString = maxSizeString;
    }

    public Long getMaxSizeBytes() {
        return maxSizeBytes;
    }

    public void setMaxSizeBytes(Long maxSizeBytes) {
        this.maxSizeBytes = maxSizeBytes;
    }
}
