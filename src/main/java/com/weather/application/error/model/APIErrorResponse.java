package com.weather.application.error.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class APIErrorResponse {
    Error error;
}
