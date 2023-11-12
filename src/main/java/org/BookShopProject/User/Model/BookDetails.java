package org.BookShopProject.User.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookDetails {
    @NotNull
    private Long bookId;
    private String name;
    private String publisher;
    private Double price;
    private Integer availableQuantity;
}
