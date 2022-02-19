package com.yegetables.service;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)

public class ApiResult<T> {
    private ApiResultStatus code = ApiResultStatus.Success;
    private String message = null;
    private T data = null;

}
