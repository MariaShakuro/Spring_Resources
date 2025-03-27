package com.example.core.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Entity of a driver")
public class DriverDto {
    @Schema(description = "Driver id")
    private Long id;
    @Schema(description = "Driver name")
    private String name;
    @Schema(description = "Driver license number")
    private String licenseNumber;
    @Schema(description = "Driver rating")
    private int rating;

}
