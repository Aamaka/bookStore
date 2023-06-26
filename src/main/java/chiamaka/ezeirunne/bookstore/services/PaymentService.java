package chiamaka.ezeirunne.bookstore.services;

import chiamaka.ezeirunne.bookstore.dto.requests.PaymentRequest;
import chiamaka.ezeirunne.bookstore.dto.responses.InitialPaymentResponse;
import chiamaka.ezeirunne.bookstore.dto.responses.InitialPaymentVerificationResponse;

public interface PaymentService {
    InitialPaymentResponse makePayment(PaymentRequest request);
    InitialPaymentVerificationResponse verifyPayment(String reference);
}
