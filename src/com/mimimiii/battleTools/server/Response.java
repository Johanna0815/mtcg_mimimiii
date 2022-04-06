package com.mimimiii.battleTools.server;


public class Response {

    private String message;
    private String contentType;
    private String content;
    private HttpStatus status;


    public Response() {
        setStatus(HttpStatus.OK);
        contentType = ContentType.HTML.type;
        content = "it works. [Response.]";
    }


    public Response(HttpStatus httpStatus, ContentType contentType, String content) {
        this.status = httpStatus.code;
        this.message = httpStatus.message;
        this.contentType = contentType.type;
        this.content = content;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType.type;
    }

    public void setContent(String content) {
        this.content = content;
    }


/*
    public Response() {

       // message = "nur für Test hier.";
        contentType = ContentType.HTML.type;
        content = "nur für Test in content";
    }

 */

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", contentType='" + contentType + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }

    //subclass can see // sends 1:1 back |  standard response
    protected String get() {
        return "HTTP/1.1 " + this.status + " " + this.message + "\r\n" +
                "Content-Type: " + this.contentType + "\r\n" +
                "Content-Length: " + this.content.length() + "\r\n" +
                "\r\n" +
                this.content;
    }
}