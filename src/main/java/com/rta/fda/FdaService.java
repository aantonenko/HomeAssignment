package com.rta.fda;

import com.rta.model.DrugRecordApplication;

import java.util.List;

public interface FdaService {

    List<DrugRecordApplication> search(String manufactureName, String brandName, int page);
}
