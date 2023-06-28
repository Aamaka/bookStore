package chiamaka.ezeirunne.bookstore.data.models.users;

import chiamaka.ezeirunne.bookstore.enums.Authority;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    private String name;

    @Column(unique = true)
    @Email
    private String email;

    private String password; @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Authority> authority = new HashSet<>();
    private String createdDate;
    private String modifiedDate;

}
