package org.BookShopProject.User.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.BookShopProject.User.Model.BookDetails;
import org.BookShopProject.User.Model.TransactionBookDetails;
import org.BookShopProject.User.Repository.UserRepository;
import org.BookShopProject.User.Entity.UserDetailsEntity;
import org.BookShopProject.User.Model.BookTransactionDetails;
import org.BookShopProject.User.Model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${application.book.name}")
    private String bookService;

    @Value("${application.transaction.name}")
    private String transactionService;

    @Override
    public String addUser(UserDetails user) {
        UserDetailsEntity userDetailsEntity = UserDetailsEntity.builder()
                .name(user.getName())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .emailId(user.getEmailId()).build();
        try {
            userRepository.save(userDetailsEntity);
            return "User Added Successfully";
        } catch (Exception e) {
            return "User Addition Failed. Error: " + e.getMessage();
        }
    }

    @Override
    public List<UserDetails> getUser() {
        List<UserDetailsEntity> listUsers = userRepository.findAll();
        List<UserDetails> listUserDetails = new ArrayList<>();
        for (UserDetailsEntity user: listUsers) {
            UserDetails userDetails = new UserDetails();
            userDetails.setId(user.getUserId());
            userDetails.setName(user.getName());
            userDetails.setAddress(user.getAddress());
            userDetails.setEmailId(user.getEmailId());
            userDetails.setPhoneNumber(user.getPhoneNumber());
            listUserDetails.add(userDetails);
        }
        return listUserDetails;
    }

    @Override
    public UserDetails getUserById(Long id) throws Exception {
        Optional<UserDetailsEntity> singleUser = userRepository.findById(id);
        UserDetails userDetails = new UserDetails();
       if (singleUser.isPresent()) {
           userDetails.setId(singleUser.get().getUserId());
           userDetails.setName(singleUser.get().getName());
           userDetails.setAddress(singleUser.get().getAddress());
           userDetails.setEmailId(singleUser.get().getEmailId());
           userDetails.setPhoneNumber(singleUser.get().getPhoneNumber());
       }
       else {
           userDetails.setError("No User Found!");
       }
        return userDetails;
    }

    @Override
    public String deleteUser(Long id) throws Exception {
        try {
            Optional<UserDetailsEntity> userDetailsOptional = userRepository.findById(id);
            if(userDetailsOptional.isPresent()){
                userRepository.deleteById(id);
            }else{
                throw new SQLException("User not found");
            }
            return "User Id " + id + " deleted Successfully!";
        } catch(Exception e) {
            return " Error in deleting user : " + e.getMessage();
        }
    }

    @Override
    public ResponseEntity<String> updateBookTransaction(BookTransactionDetails bookTransactionDetails) throws Exception {

        Set<TransactionBookDetails> transactionBookDetailsSet = new HashSet<>();

        try {
            // START TO CHECK IF USER AVAILABLE
            String strUserEmailId = bookTransactionDetails.getUserEmailId();
            Optional<UserDetailsEntity> useDetailsEntityOptional = userRepository.findByEmailId(strUserEmailId);
            UserDetailsEntity useDetailsEntity = useDetailsEntityOptional.orElseThrow(() -> new Exception("User Not Found!"));
            // END


            for (TransactionBookDetails transactionBookDetails : bookTransactionDetails.getTransactionBooks()) {
                ResponseEntity<BookDetails> responseBook = restTemplate.getForEntity(bookService+"/books/getBook/"
                        + transactionBookDetails.getBook().getBookId(), BookDetails.class);

                BookDetails bookDetails = responseBook.getBody();
                transactionBookDetails.setBook(bookDetails);

                transactionBookDetailsSet.add(transactionBookDetails);
            }

            bookTransactionDetails.setTransactionBooks(transactionBookDetailsSet);
            String transactionDetail = objectMapper.writeValueAsString(bookTransactionDetails);
            System.out.println("transactionDetail->"+transactionDetail);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestTransaction = new HttpEntity<String>(transactionDetail, headers);

            ResponseEntity<String> transactionAdded = restTemplate.postForEntity(transactionService + "/transaction/addBook", requestTransaction, String.class);

            return transactionAdded;

        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
