package com.yegetables.controller.dto;

import lombok.Getter;

@Getter
public enum ApiResultStatus {
    Success, Error, NotFound, Unauthorized, Forbidden, BadRequest, InternalServerError, BadGateway, ServiceUnavailable, GatewayTimeout, TooManyRequests
}
