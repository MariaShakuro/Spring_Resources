package com.example.core.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Entity of a passenger")
public class PassengerDto {
    @Schema(description = "Passenger id")
    private Long id;
    @Schema(description = "Passenger name")
    private String name;
    @Schema(description = "Passenger email")
    private String email;
    @Schema(description = "Passenger password")
    private String password;
    @Schema(description = "Passenger phone number")
    private String phoneNumber;
    @Schema(description = "Passenger promocode")
    private String promocode;

}

