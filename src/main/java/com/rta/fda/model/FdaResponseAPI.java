package com.rta.fda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rta.model.DrugRecordApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FdaResponseAPI {

    private List<ResultAPI> results;

    public List<DrugRecordApplication> toModel() {
        return results.stream().map(e ->
                new DrugRecordApplication(
                        e.getApplicationNumber(),
                        e.getOpenFda().getManufacturerName(),
                        e.getOpenFda().getBrandName(),
                        e.getOpenFda().getSubstanceName(),
                        e.getProducts().stream().map(ProductAPI::getProductNumber).collect(Collectors.toList())
                        )
        ).collect(Collectors.toList());
    }
}
