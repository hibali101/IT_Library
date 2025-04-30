package com.hibali.IT_Library.http.server.responses;

public abstract class BaseResponse {
    private int responseCode;
    private byte[] data;
    private String contentType;

    protected BaseResponse(int responseCode, byte[] data, String contentType) {
        this.responseCode = responseCode;
        this.data = data;
        this.contentType = contentType;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public byte[] getData() {
        return data;
    }

    public String getContentType() {
        return contentType;
    }
}
