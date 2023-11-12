package org.BookShopProject.User.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.BookShopProject.User.Model.BookTransactionDetails;
import org.BookShopProject.User.Model.TransactionBookDetails;
import org.BookShopProject.User.Model.UserDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface IUserService {

    public String addUser(UserDetails user);

    public List<UserDetails> getUser();

    public UserDetails getUserById(Long id) throws Exception;

    public String deleteUser(Long id) throws Exception;

    public ResponseEntity<String> updateBookTransaction(BookTransactionDetails bookTransactionDetails) throws Exception;
}
