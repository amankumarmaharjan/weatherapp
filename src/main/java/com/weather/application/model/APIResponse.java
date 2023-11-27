package com.weather.application.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class APIResponse {
    Object data;
}
