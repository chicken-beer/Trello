package com.example.trello.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final int status;

    public CommonResponseDto(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}
