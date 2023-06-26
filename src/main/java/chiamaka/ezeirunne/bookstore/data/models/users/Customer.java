package chiamaka.ezeirunne.bookstore.data.models.users;

import chiamaka.ezeirunne.bookstore.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String phoneNumber;

    private String address;

}
