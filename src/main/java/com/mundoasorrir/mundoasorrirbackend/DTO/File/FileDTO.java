package com.mundoasorrir.mundoasorrirbackend.DTO.File;

public class FileDTO {

    private String sharedBy;

    private String name;
    private String url;
    private String type;
    private long size;

    public FileDTO(String name, String url, String type, long size, String sharedBy) {
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
        this.sharedBy = sharedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getSharedBy() {
        return sharedBy;
    }

    public void setSharedBy(String sharedBy) {
        this.sharedBy = sharedBy;
    }
}
