package com.mimimiii.battleTools.server;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Request {
    private Crud_Method crud_Method;
    private String pathname;
    private String params; // (content)
    private String contentType;
    private Integer contentLength;
    private String body = "";

    @Override
    public String toString() {
        return "Request{" +
                "crud_Method=" + crud_Method +
                ", pathname='" + pathname + '\'' +
                ", params='" + params + '\'' +
                ", contentType='" + contentType + '\'' +
                ", contentLength=" + contentLength +
                ", body='" + body + '\'' +
                '}';
    }
}
