package chiamaka.ezeirunne.bookstore.dto.responses;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentVerificationResponse {
    private Long id;
    private String domain;
    private String status;
    private String reference;
    private BigDecimal amount;
    private String message;



}
