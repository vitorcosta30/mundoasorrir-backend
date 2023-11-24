package com.mundoasorrir.mundoasorrirbackend.Message;

import java.util.Date;

public class ResponseEvent {

    private String start;

    private String end;

    private String title;

    private String type;

    private Long id;


    public ResponseEvent(String type,String start, String end, String title, Long id) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.title = title;
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
