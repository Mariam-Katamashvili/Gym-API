package com.mariamkatamashvlii.gym.dto.userDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class NewPasswordDTO {
    @Valid
    @NotNull
    private String username;

    @Valid
    @NotNull
    private String currentPass;

    @Valid
    @NotNull
    private String newPass;
}
