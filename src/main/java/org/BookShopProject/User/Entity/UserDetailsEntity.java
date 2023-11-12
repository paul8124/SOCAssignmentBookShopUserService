package org.BookShopProject.User.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UserDetails")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column
    private String name;
    @Column
    private String emailId;
    @Column
    private String phoneNumber;
    @Column
    private String address;

}
