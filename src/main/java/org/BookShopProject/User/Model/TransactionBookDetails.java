package org.BookShopProject.User.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class TransactionBookDetails {

    private Integer bookTransactionId;

    @Valid
    private BookDetails book;

    @PositiveOrZero(message = "{transactionBook.invalid.quantity}")
    private Integer quantity;

}
