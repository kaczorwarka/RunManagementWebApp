package com.WCY20IJ1S1.RunManagement.Service;

import com.WCY20IJ1S1.RunManagement.Model.Run;
import com.WCY20IJ1S1.RunManagement.Model.User;
import com.WCY20IJ1S1.RunManagement.Repository.RunRepository;
import com.WCY20IJ1S1.RunManagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RunRepository runRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password){
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    public User addUser(String email, String password, String name,
                        String lastName, String gender, String dateOfBirth){
        return userRepository.insert(
                new User(
                        email,
                        password,
                        name,
                        lastName,
                        gender,
                        dateOfBirth
                )
        );
    }

    public User updateUserData(String oldEmail, String oldPassword, String newEmail,
                               String newPassword, String name, String lastName,
                               String gender, String dateOfBirth){
        Optional<User> optionalUser = userRepository.findUserByEmailAndPassword(oldEmail, oldPassword);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if (!Objects.equals(newEmail, "")){
                List<Run> runs = runRepository.findAllByUserEmail(oldEmail);
                for (Run run : runs) {
                    run.setUserEmail(newEmail);
                    runRepository.save(run);
                }
                user.setEmail(newEmail);
            }
            if (!Objects.equals(newPassword, "")){
                user.setPassword(newPassword);
            }
            if (!Objects.equals(name, "")){
                user.setName(name);
            }
            if (!Objects.equals(lastName, "")) {
                user.setLastName(lastName);
            }
            user.setGender(gender);
            if (!Objects.equals(dateOfBirth, "")) {
                user.setDateOfBirth(dateOfBirth);
            }
            return userRepository.save(user);
        }else{
            return null;
        }
    }

    public void deleteUser(String email, String password){
        Optional<User> user = userRepository.findUserByEmailAndPassword(email, password);
        if (user.isPresent()){
            runRepository.deleteAllByUserEmail(email);
        }
        userRepository.deleteUserByEmailAndPassword(email, password);
    }

    public byte[] saveImage(MultipartFile file, String userEmail, String userPassword) {

        Optional<User> optionalUser = userRepository.findUserByEmailAndPassword(userEmail, userPassword);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            try {
                user.setImage(file.getBytes());
                userRepository.save(user);
                return file.getBytes();
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public byte[] getImage(String userEmail, String userPassword) {
        Optional<User> optionalUser = userRepository.findUserByEmailAndPassword(userEmail, userPassword);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user.getImage();
        } else {
            return new byte[0];
        }
    }
}
