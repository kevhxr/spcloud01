package com.xr.xrservice1.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockInfo {
    private String companyName;
    private Integer stockId;
}
