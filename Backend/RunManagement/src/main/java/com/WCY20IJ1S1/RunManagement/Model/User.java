package com.WCY20IJ1S1.RunManagement.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private byte[] image;

    public User(String email, String password, String name, String lastName, String gender, String dateOfBirth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.image = new byte[]{};
    }
}
