package com.example.demo_mall.mallapi.dto.memberaddress;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddressAddReqDto {
    private Long memberId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String zipCode;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String addressDetail;
}
