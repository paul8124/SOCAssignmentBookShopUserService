package org.BookShopProject.User.Model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Set;

@Data
public class BookTransactionDetails {

    private Integer transactionId;
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+" ,
            message = "{invalid.userEmail.format}")
    private String userEmailId;
    @Valid
    private Set<TransactionBookDetails> transactionBooks;

}
