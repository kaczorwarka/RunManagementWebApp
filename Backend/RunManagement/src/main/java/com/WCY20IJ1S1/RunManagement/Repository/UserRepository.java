package com.WCY20IJ1S1.RunManagement.Repository;

import com.WCY20IJ1S1.RunManagement.Model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findUserByEmail(String Email);

    Optional<User> findUserByEmailAndPassword(String Email, String Password);

    void deleteUserByEmail(String Email);

    void deleteUserByEmailAndPassword(String Email, String Password);
}
