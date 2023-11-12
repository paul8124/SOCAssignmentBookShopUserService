package org.BookShopProject.User.Controller;


import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import org.BookShopProject.User.Model.TransactionBookDetails;
import org.BookShopProject.User.Service.IUserService;
import org.BookShopProject.User.Model.BookTransactionDetails;
import org.BookShopProject.User.Model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private IUserService userService;

    // Get All Users
    @GetMapping(value = "/getUsers")
    public ResponseEntity<List<UserDetails>> getUsers() throws Exception {
        List<UserDetails> registeredUsers = userService.getUser();
        return new ResponseEntity<>(registeredUsers, HttpStatus.OK);
    }

    //Get Single User By Id
    @GetMapping(value = "/getUser/{id}")
    public ResponseEntity<UserDetails> getUser(@PathVariable Long id) throws Exception {
        UserDetails registeredUser = userService.getUserById(id);
        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }

    // Add New User
    @PostMapping(value = "/addUser")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserDetails user) throws Exception {

        String registeredWithEmailID = userService.addUser(user);
        return new ResponseEntity<>(registeredWithEmailID, HttpStatus.OK);
    }

    // Delete User By Id
    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws Exception {

        String registeredWithID = userService.deleteUser(id);
        return new ResponseEntity<>(registeredWithID, HttpStatus.OK);
    }


    // Add Book to Transaction
    @PostMapping(value = "/transaction/addTransaction")
    public ResponseEntity<String> addTransaction(@Valid @RequestBody BookTransactionDetails bookTransactionDetails) throws Exception {

        ResponseEntity<String> bookUpdate = userService.updateBookTransaction(bookTransactionDetails);
        return bookUpdate;
    }

}
