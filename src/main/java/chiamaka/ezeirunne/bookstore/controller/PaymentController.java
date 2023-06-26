package chiamaka.ezeirunne.bookstore.controller;

import chiamaka.ezeirunne.bookstore.dto.requests.PaymentRequest;
import chiamaka.ezeirunne.bookstore.dto.responses.InitialPaymentResponse;
import chiamaka.ezeirunne.bookstore.dto.responses.InitialPaymentVerificationResponse;
import chiamaka.ezeirunne.bookstore.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/")
    public InitialPaymentResponse makePayment(@RequestBody PaymentRequest request){
        return paymentService.makePayment(request);
    }

    @GetMapping("/{reference}")
    public InitialPaymentVerificationResponse verifyPayment(@PathVariable String reference){
        return paymentService.verifyPayment(reference);
    }
}
