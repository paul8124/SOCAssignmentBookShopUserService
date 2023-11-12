package org.BookShopProject.User.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
public class UserDetails {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    @NotNull(message = "{email.absent}")
    @Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+",
            message = "{invalid.email.format}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String emailId;

    @Size(max=10, min = 10, message = "{customer.invalid.phoneNumber}")
    @Pattern(regexp = "[0-9]+", message = "{customer.invalid.phoneNumber}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String phoneNumber;

    @NotNull(message = "{customer.address.absent}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

}
