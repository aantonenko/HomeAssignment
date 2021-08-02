package com.rta.fda.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FdaResponseAPITest {

    @Test
    void fdaResponseAPITest() throws IOException {
        FdaResponseAPI res = new ObjectMapper().readValue(getResource("some_fda_response.json"), FdaResponseAPI.class);
        assert res.getResults().size() == 2;
    }

    private String getResource(String fileName) throws IOException {
        return new String(Files.readAllBytes(new File(getClass().getClassLoader().getResource(fileName).getFile()).toPath()));
    }
}
