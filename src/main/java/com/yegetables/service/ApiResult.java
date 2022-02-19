package com.yegetables.service;

import com.google.gson.Gson;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class ApiResult<T> {

    private ApiResultStatus code = ApiResultStatus.Success;
    private String message = null;
    private T data = null;

    public String toString() {
        return new Gson().toJson(this);
    }
}
