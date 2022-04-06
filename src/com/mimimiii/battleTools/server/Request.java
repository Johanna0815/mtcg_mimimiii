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
    private String authorization;


    public Crud_Method getCrud_Method() {
        return crud_Method;
    }

    public void setCrud_Method(Crud_Method crud_Method) {
        this.crud_Method = crud_Method;
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public Crud_Method getMethod() {
        return Crud_Method.POST;
    }

    public String getAuthorization() {
        return authorization;
    }
    // en bug------------------TEST 1------------------------------
    public String getContent() {
        return contentType;
    }

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
