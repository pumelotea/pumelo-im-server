package io.pumelo.im.model;

import lombok.Data;

@Data
public class APIRespones<T> {
    private String msg;
    private int code;
    private T data;

    public APIRespones(String msg, int code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static <T> APIRespones success(T data){
        return new APIRespones("Success",200,data);
    }

    public static APIRespones failure(int code,String msg){
        return new APIRespones(msg,code,null);
    }
}
