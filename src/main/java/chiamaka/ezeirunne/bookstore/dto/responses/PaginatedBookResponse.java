package chiamaka.ezeirunne.bookstore.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedBookResponse {
    private List<BookDto> books;

    private long currentPage;

    private long numberOfBooks;

    private long noOfTotalPages;
}
