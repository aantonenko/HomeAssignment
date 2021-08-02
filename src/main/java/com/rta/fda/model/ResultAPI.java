package com.rta.fda.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultAPI {
    @JsonProperty("application_number")
    private String applicationNumber;
    @JsonProperty("openfda")
    private OpenFdaAPI openFda;
    @JsonProperty("products")
    private List<ProductAPI> products;
}
