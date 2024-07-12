package com.kss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KSSResponse {

    private String message;
    private Boolean success;
    private Object data;

    public KSSResponse(String message, boolean success) {
        this.message=message;
        this.success=success;

    }
}
