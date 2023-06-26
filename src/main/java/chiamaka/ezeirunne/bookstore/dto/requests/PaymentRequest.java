package chiamaka.ezeirunne.bookstore.dto.requests;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Setter
@Getter
@Builder
public class PaymentRequest {
    private String email;
    private BigInteger amount;

}
