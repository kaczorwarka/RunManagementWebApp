package com.WCY20IJ1S1.RunManagement.Controller;

import com.WCY20IJ1S1.RunManagement.Model.User;
import com.WCY20IJ1S1.RunManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("DB/Users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
    }
//    @GetMapping("/get/{email}")
//    public ResponseEntity<Optional<User>> getUser(@PathVariable String email){
//        return new ResponseEntity<Optional<User>>(userService.getUserByEmail(email), HttpStatus.OK);
//    }

    @GetMapping("/get/{email}/{password}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable String email, @PathVariable String password){
        Optional<User> user = userService.getUserByEmailAndPassword(email, password);
        if (user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody Map<String, String> payload){
        return new ResponseEntity<User>(
                userService.addUser(
                        payload.get("email"),
                        payload.get("password"),
                        payload.get("name"),
                        payload.get("lastName"),
                        payload.get("gender"),
                        payload.get("dateOfBirth")
                ), HttpStatus.CREATED
        );
    }

    @PutMapping("/put/{oldEmail}/{oldPassword}")
    public ResponseEntity<User> updateUser(@PathVariable String oldEmail,
                                           @PathVariable String oldPassword,
                                           @RequestBody Map<String, String> payload){
        return new ResponseEntity<User>(
                userService.updateUserData(
                        oldEmail,
                        oldPassword,
                        payload.get("email"),
                        payload.get("password"),
                        payload.get("name"),
                        payload.get("lastName"),
                        payload.get("gender"),
                        payload.get("dateOfBirth")
                ), HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{email}/{password}")
    public void deleteUser(@PathVariable String email, @PathVariable String password){
        userService.deleteUser(email, password);
    }

    @PostMapping("/uploadImage/{email}/{password}")
    public ResponseEntity<byte[]> uploadImage(@PathVariable String email,
                                              @PathVariable String password,
                                              @RequestParam("file") MultipartFile file) {
        byte[] imageData = userService.saveImage(file, email, password);
        return ResponseEntity.ok().body(imageData);
    }

    @GetMapping("/getImage/{email}/{password}")
    public ResponseEntity<byte[]> getImage(@PathVariable String email,
                                           @PathVariable String password) {
        byte[] imageData = userService.getImage(email, password);
        return ResponseEntity.ok().body(imageData);
    }
}
