package chiamaka.ezeirunne.bookstore.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InitialPaymentVerificationResponse {
    private boolean status;
    private String message;
    private PaymentVerificationResponse data;
}

