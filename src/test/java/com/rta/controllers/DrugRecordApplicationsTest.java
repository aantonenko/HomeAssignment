package com.rta.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rta.db.DrugRecordApplicationRepository;
import com.rta.fda.FdaService;
import com.rta.model.DrugRecordApplication;
import com.rta.model.DrugRecordApplicationDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DrugRecordApplications.class)
public class DrugRecordApplicationsTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    DrugRecordApplicationRepository drugRecordApplicationRepository;

    @MockBean
    FdaService fdaService;

    DrugRecordApplication testApplication = DrugRecordApplication.builder()
            .applicationNumber("test_app_number")
            .manufacturerName(List.of("test_manufacturer_name"))
            .brandName(List.of("test_brand_name"))
            .manufacturerName(List.of("test_substance_name"))
            .productNumbers(List.of("test_product_numbers"))
            .build();

    @Test
    void searchTest() throws Exception {

        Mockito.when(fdaService.search(anyString(), anyString(), anyInt())).thenReturn(List.of(testApplication));
        RequestBuilder request = MockMvcRequestBuilders.get("/fda")
                .queryParam("manufactureName", "test_manufacture")
                .queryParam("brandName", "test_brand");
        MvcResult mvcResult = mvc.perform(request).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = "[{\"applicationNumber\":\"test_app_number\",\"manufacturerName\":[\"test_substance_name\"],\"brandName\":[\"test_brand_name\"],\"substanceName\":null,\"productNumbers\":[\"test_product_numbers\"]}]";
        JSONAssert.assertEquals(expectedResponseBody, actualResponseBody, JSONCompareMode.STRICT);
    }

    @Test
    void saveApplicationTest() throws Exception {

        Mockito.when(drugRecordApplicationRepository.save(any())).thenReturn(testApplication);
        RequestBuilder request = MockMvcRequestBuilders.post("/applications")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(DrugRecordApplicationDTO.fromModel(testApplication)));
        mvc.perform(request).andExpect(status().isCreated());

        verify(drugRecordApplicationRepository, times(1)).save(any());
    }

    @Test
    void loadApplicationsTest() throws Exception {

        Mockito.when(drugRecordApplicationRepository.findAll()).thenReturn(List.of(testApplication));
        RequestBuilder request = MockMvcRequestBuilders.get("/applications");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = "[{\"applicationNumber\":\"test_app_number\",\"manufacturerName\":[\"test_substance_name\"],\"brandName\":[\"test_brand_name\"],\"substanceName\":null,\"productNumbers\":[\"test_product_numbers\"]}]";
        JSONAssert.assertEquals(expectedResponseBody, actualResponseBody, JSONCompareMode.STRICT);
    }

    @Test
    void loadApplicationByIdTest() throws Exception {

        Mockito.when(drugRecordApplicationRepository.findById("test_app_number")).thenReturn(Optional.of(testApplication));
        RequestBuilder request = MockMvcRequestBuilders.get("/applications/test_app_number");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = "{\"applicationNumber\":\"test_app_number\",\"manufacturerName\":[\"test_substance_name\"],\"brandName\":[\"test_brand_name\"],\"substanceName\":null,\"productNumbers\":[\"test_product_numbers\"]}";
        JSONAssert.assertEquals(expectedResponseBody, actualResponseBody, JSONCompareMode.STRICT);
    }

}
