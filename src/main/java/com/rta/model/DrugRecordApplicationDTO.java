package com.rta.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DrugRecordApplicationDTO {
    @ApiModelProperty("Unique application number")
    private String applicationNumber;
    @ApiModelProperty("Name of manufacturer or company that makes this drug product, corresponding to the labeler code segment of the NDC.")
    private List<String> manufacturerName;
    @ApiModelProperty("Brand or trade name of the drug product.")
    private List<String> brandName;
    @ApiModelProperty("The list of active ingredients of a drug product")
    private List<String> substanceName;
    @ApiModelProperty("A product number is assigned to each drug product associated with an NDA (New Drug Application), ANDA (Abbreviated New Drug Application) and Biologic License Application (BLA). If a drug product is available in multiple strengths, there are multiple product numbers")
    private List<String> productNumbers;

    public DrugRecordApplication toModel() {
        return DrugRecordApplication.builder()
                .applicationNumber(applicationNumber)
                .manufacturerName(manufacturerName)
                .brandName(brandName)
                .substanceName(substanceName)
                .productNumbers(productNumbers)
                .build();
    }

    public static DrugRecordApplicationDTO fromModel(DrugRecordApplication drugRecordApplication) {
        return builder()
                .applicationNumber(drugRecordApplication.getApplicationNumber())
                .manufacturerName(drugRecordApplication.getManufacturerName())
                .brandName(drugRecordApplication.getBrandName())
                .substanceName(drugRecordApplication.getSubstanceName())
                .productNumbers(drugRecordApplication.getProductNumbers())
                .build();
    }
}
