package com.WCY20IJ1S1.RunManagement.Repository;

import com.WCY20IJ1S1.RunManagement.Model.Run;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RunRepository extends MongoRepository<Run, ObjectId> {

    Optional<Run> findRunByName(String name);

    List<Run> findAllByUserEmail(String userEmail);

    Optional<Run> findRunByUserEmailAndName(String userEmail, String name);

    void deleteRunByName(String name);

    void deleteRunByNameAndUserEmail(String name, String userEmail);

    void deleteAllByUserEmail(String userEmail);

}
