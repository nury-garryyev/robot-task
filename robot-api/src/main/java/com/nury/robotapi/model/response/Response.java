package com.nury.robotapi.model.response;

import com.nury.robotapi.model.Robot;

public class Response {

    private String status;
    private Robot data;
    private ResponseError error;

    public Response(Robot data) {
        this.data = data;
        this.error = null;
        this.status = data != null ? "success" : "failure";
    }

    public Response(String error) {
        this.error = new ResponseError(error);
        this.data = null;
        this.status = "failure";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Robot getData() {
        return data;
    }

    public void setData(Robot data) {
        this.data = data;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}
