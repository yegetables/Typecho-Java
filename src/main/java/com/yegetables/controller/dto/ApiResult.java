package com.yegetables.controller.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

@Data
@Accessors(fluent = true)
@Log4j2
public class ApiResult<T> implements Serializable {

    private ApiResultStatus code = ApiResultStatus.Success;
    private String message = null;
    private T data = null;

    public String toString() {
        try
        {
            // if (data != null)
                // log.warn(data.toString());
                           data.toString();
            return JSON.toJSONString(this);
        } catch (Exception e)
        {
            log.warn("ApiResult toString error", e);
            return "";
        }
    }

    public ApiResultStatus getCode() {
        return code;
    }

    public ApiResult<T> setCode(ApiResultStatus code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ApiResult<T> setData(T data) {
        this.data = data;
        return this;
    }
}
