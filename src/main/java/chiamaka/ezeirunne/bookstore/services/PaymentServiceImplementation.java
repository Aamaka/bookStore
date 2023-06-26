package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.PaymentRequest;
import chiamaka.ezeirunne.bookstore.dto.responses.InitialPaymentResponse;
import chiamaka.ezeirunne.bookstore.dto.responses.InitialPaymentVerificationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigInteger;

@Service
public class PaymentServiceImplementation implements PaymentService {
    @Value("${secretKey}")
    private String key;

    @Override
    public InitialPaymentResponse makePayment(PaymentRequest request) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer "+key);
        request.setAmount(request.getAmount().multiply(BigInteger.valueOf(100)));
        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<InitialPaymentResponse> response = restTemplate.postForEntity("https://api.paystack.co/transaction/initialize", requestEntity, InitialPaymentResponse.class);
        return response.getBody();
    }

    @Override
    public InitialPaymentVerificationResponse verifyPayment(String reference) {
        String url = "https://api.paystack.co/transaction/verify/" + reference;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer "+key);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<InitialPaymentVerificationResponse> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, InitialPaymentVerificationResponse.class);

        return response.getBody();
    }
}
