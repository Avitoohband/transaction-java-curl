package com.avi.transaction.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BetweenDatesRequest {
    private LocalDate from;
    private LocalDate to;
}
