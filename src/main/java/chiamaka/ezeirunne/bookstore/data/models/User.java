package chiamaka.ezeirunne.bookstore.data.models;

import chiamaka.ezeirunne.bookstore.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
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

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String createdDate;
    private String modifiedDate;

}
