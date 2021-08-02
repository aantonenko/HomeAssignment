package com.rta.fda;

import com.rta.fda.model.FdaResponseAPI;
import com.rta.model.DrugRecordApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FdaServiceImpl implements FdaService {

    private final static int ENTITY_PER_PAGE = 10;

    private final String fdaApiUrl;
    private final RestTemplate fdaRestTemplate;

    @Autowired
    public FdaServiceImpl(@Value("${fda.api.host}") String fdaApiUrl,
                          RestTemplate fdaRestTemplate) {
        this.fdaApiUrl = fdaApiUrl;
        this.fdaRestTemplate = fdaRestTemplate;
    }

    @Override
    public List<DrugRecordApplication> search(String manufactureName, String brandName, int page) {
        Map<String, String> params = new HashMap<>();
        params.put("openfda.manufacturer_name", manufactureName);
        if (brandName != null) {
            params.put("openfda.brand_name", brandName);
        }
        String url = createUrl(params, page);
        ResponseEntity<FdaResponseAPI> fdaResponse = fdaRestTemplate.getForEntity(url, FdaResponseAPI.class);
        return fdaResponse.getBody().toModel();
    }

    private String createUrl(Map<String, String> params, int page) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fdaApiUrl);
        builder.queryParam("search", constructSearchRequest(params));
        builder.queryParam("skip", ENTITY_PER_PAGE * page);
        builder.queryParam("limit", ENTITY_PER_PAGE);
        return builder.toUriString();
    }

    private String constructSearchRequest(Map<String, String> params) {
        List<String> paramsStr = params.entrySet().stream()
                .map(e -> e.getKey() + ":" + e.getValue())
                .collect(Collectors.toList());
        return String.join("+AND+", paramsStr);
    }
}
