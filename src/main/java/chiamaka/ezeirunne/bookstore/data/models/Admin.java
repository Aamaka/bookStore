package chiamaka.ezeirunne.bookstore.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Admin extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
