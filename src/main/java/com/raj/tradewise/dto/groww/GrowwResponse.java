package com.raj.tradewise.dto.groww;

import jakarta.validation.Payload;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrowwResponse {

//    private String status;
    private Payload payload;

    @Getter
    @Setter
    public static class Payload{
        private List<GrowwHoldingResponse> holdings;
    }
}
