package mixss.github_api.apiclients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mixss.github_api.execptions.ApiErrorReposnseException;
import mixss.github_api.execptions.ApiNotFoundException;
import mixss.github_api.execptions.ApiResponseBadFormatException;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public abstract class ApiClient {
    // this abstract class implements call to an external api

    public JsonNode makeCall(String url) throws ApiNotFoundException, ApiResponseBadFormatException, ApiErrorReposnseException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> httpEntity = new HttpEntity<>("body", headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(response.getBody());
        } catch(HttpClientErrorException e) {
            HttpStatus status = (HttpStatus) e.getStatusCode();
            if(status == HttpStatus.NOT_FOUND){
                throw new ApiNotFoundException();
            }
            throw new ApiErrorReposnseException(e.getMessage());
        } catch (JsonProcessingException e) {
            throw new ApiResponseBadFormatException();
        }
    }
}
