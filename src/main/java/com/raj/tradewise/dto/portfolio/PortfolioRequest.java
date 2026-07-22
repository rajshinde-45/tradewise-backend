package com.raj.tradewise.dto.portfolio;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioRequest {

    @NotBlank(message = "Portfolio name is required")
    private String name;
//    no need of email
//    @Email
//    private String email;



}
