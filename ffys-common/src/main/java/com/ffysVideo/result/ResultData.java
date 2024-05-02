package com.ffysVideo.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultData<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResultData<T> success() {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(200);
        return resultData;
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(200);
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> error(String msg) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setMessage(msg);
        resultData.setCode(0);
        return resultData;
    }
}
