package com.example.drivewatch.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterOutput {

    private String registerClass;
    private String idDevice;
    private String idCompany;
    private LocalDateTime occurrenceDate;
    private String image;
}
