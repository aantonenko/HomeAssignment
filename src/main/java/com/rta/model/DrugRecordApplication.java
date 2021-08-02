package com.rta.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "applications")
@Table(name = "applications")
public class DrugRecordApplication {
    @Id
    private String applicationNumber;
    @ElementCollection
    private List<String> manufacturerName;
    @ElementCollection
    private List<String> brandName;
    @ElementCollection
    private List<String> substanceName;
    @ElementCollection
    private List<String> productNumbers;
}
