package com.flab.makedel.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RiderDTO {

    @NotNull
    private final String id;

    @NotNull
    private final String name;

    @NotNull
    private final String phone;

    @NotNull
    private final String address;

    @NotNull
    private final LocalDateTime updatedAt = LocalDateTime.now();

}
