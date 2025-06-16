package io.github.angel.raa.utils;

import org.springframework.http.HttpStatus;

import io.github.angel.raa.dto.response.Response;

public class ResponseUtils {
    private ResponseUtils() {

    }

    public static <T> HttpStatus mapToHttpStatus(Response<T> response) {
        if (response == null) {
            response.setMessage("Response is null");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        HttpStatus httpStatus = switch (response.getCode()) {
            case 0 -> HttpStatus.OK;
            case 201 -> HttpStatus.CREATED;
            case 204 -> HttpStatus.NO_CONTENT;
            case 400 -> HttpStatus.BAD_REQUEST;
            case 401 -> HttpStatus.UNAUTHORIZED;
            case 403 -> HttpStatus.FORBIDDEN;
            case 404 -> HttpStatus.NOT_FOUND;
            case 500 -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        return httpStatus;
    }
}
