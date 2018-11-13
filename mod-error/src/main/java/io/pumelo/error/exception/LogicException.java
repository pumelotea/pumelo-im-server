package io.pumelo.error.exception;

import io.pumelo.common.web.ApiResponse;

/**
 * 逻辑异常
 * author: pumelo
 * 2018/5/3
 */
public class LogicException extends RuntimeException{
    private ApiResponse apiResponse;
    public LogicException(ApiResponse apiResponse) {
        super(apiResponse.getMessage());
        this.apiResponse = apiResponse;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }
}
