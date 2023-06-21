package chiamaka.ezeirunne.bookstore.dto.responses;

import chiamaka.ezeirunne.bookstore.data.models.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Book book;
    private long numberOfOrders;
    private long numberOfBooks;
}
