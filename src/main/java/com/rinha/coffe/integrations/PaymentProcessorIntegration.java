package com.rinha.coffe.integrations;

import com.rinha.coffe.models.dtos.PaymentProcessorSendDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class PaymentProcessorIntegration {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentProcessorIntegration.class);

    public static int doPostPayment(PaymentProcessorSendDTO dto){

        String url = "http://localhost:8001/payments";

        String url_2 = "http://localhost:8002/payments";

        //LOG.info(">> POST /payments: {} - {} ", url, dto);

        int status_code = doPost(dto, url);

        //LOG.info(">> Recieve: {} ", status_code);

        if(status_code == -1){
            //LOG.info(">> POST /payments: {} - {} ", url_2, dto);
            doPost(dto, url_2);
            return 2;
        }

        return 1;
    }

    private static int doPost(PaymentProcessorSendDTO dto, String url){

        RestTemplate rest_template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        JsonbConfig config = new JsonbConfig();
        Jsonb jsonb = JsonbBuilder.create(config);

        String json = jsonb.toJson(dto);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = null;

        try{
            response = rest_template.exchange(
                url , HttpMethod.POST, request, String.class
            );
        } catch (RestClientResponseException e ) {
            LOG.error("HTTP {}, {}, {}, {}:",e.getRawStatusCode(), url, e.getMessage(), json);
            return -1;
        } catch (RestClientException e) {
            LOG.error("Erro na chamada POST para {}: {}", url, e.getMessage(), e);
            return -1;
        }

        return response.getStatusCode().value();
    }
}
