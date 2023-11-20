package com.WCY20IJ1S1.RunManagement.Service;

import com.WCY20IJ1S1.RunManagement.Model.Run;
import com.WCY20IJ1S1.RunManagement.Model.User;
import com.WCY20IJ1S1.RunManagement.Repository.RunRepository;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class RunService {

    @Autowired
    private RunRepository runRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Run> getAllRuns() {
        return runRepository.findAll();
    }

    public Optional<Run> getRun(String name) {
        return runRepository.findRunByName(name);
    }

    public List<Run> getRunsByUserEmailAndPassword(String email, String password) {
        Optional<User> user = userService.getUserByEmailAndPassword(email, password);

        if (user.isPresent()) {
            Query query = new Query()
                    .with(Sort.by(Sort.Order.asc("date")))
                    .addCriteria(Criteria.where("userEmail").is(user.get().getEmail()));

            return mongoTemplate.find(query, Run.class, "Runs");
        } else {
            return List.of();
        }
    }

    public List<Run> getFutureRunsByUserEmailAndPassword(String email, String password) {
        Optional<User> user = userService.getUserByEmailAndPassword(email, password);

        if (user.isPresent()) {
            Query query = new Query()
                    .with(Sort.by(Sort.Order.asc("date")))
                    .addCriteria(Criteria.where("userEmail").is(user.get().getEmail())
                            .and("date").gt(LocalDate.now()));

            return mongoTemplate.find(query, Run.class, "Runs");
        } else {
            return List.of();
        }
    }

    public List<Run> getPastRunsByUserEmailAndPassword(String email, String password) {
        Optional<User> user = userService.getUserByEmailAndPassword(email, password);

        if (user.isPresent()) {
            Query query = new Query()
                    .with(Sort.by(Sort.Order.asc("date")))
                    .addCriteria(Criteria.where("userEmail").is(user.get().getEmail())
                            .and("date").lt(LocalDate.now()));

            return mongoTemplate.find(query, Run.class, "Runs");
        } else {
            return List.of();
        }
    }

    public Run addRun(String name, String distance, String dateString, String localization, String website,
                      String type, String time, String rankingPlace, String userEmail) {

        dateString += "T00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        if (Objects.equals(rankingPlace, "")) {
            rankingPlace = "0";
        }
        try {
            Date date = sdf.parse(dateString);
            return runRepository.insert(
                    new Run(
                            name,
                            distance,
                            date,
                            localization,
                            website,
                            type,
                            time,
                            Integer.parseInt(rankingPlace),
                            userEmail));
        } catch (ParseException e) {
            return null;
        }
    }

    public Run updateRunData(String oldName, String newName, String distance, String dateString,
                             String localization, String website, String type, String time,
                             String rankingPlace, String userEmail, String userPassword) {
        Optional<User> user = userService.getUserByEmailAndPassword(userEmail, userPassword);
        if (user.isPresent()) {
            Optional<Run> optionalRun = runRepository.findRunByUserEmailAndName(userEmail, oldName);
            if (optionalRun.isPresent()) {
                dateString += "T12:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                try{
                    Run run = optionalRun.get();
                    if (!Objects.equals(newName, "")) {
                        run.setName(newName);
                    }
                    if (!Objects.equals(distance, "")) {
                        run.setDistance(distance);
                    }
                    if (!dateString.equals("T12:00:00")) {
                        Date date = sdf.parse(dateString);
                        run.setDate(date);
                    }
                    if (!Objects.equals(localization, "")) {
                        run.setLocalization(localization);
                    }
                    if (!Objects.equals(website, "")) {
                        run.setWebsite(website);
                    }
                    if (!Objects.equals(type, "")) {
                        run.setType(type);
                    }
                    if (!Objects.equals(time, "")) {
                        run.setTime(time);
                    }
                    if (!Objects.equals(rankingPlace, "")){
                        run.setRankingPlace(Integer.parseInt(rankingPlace));
                    }
                    return runRepository.save(run);
                } catch (ParseException e){
                    return null;
                }
            }
        }
        return null;
    }

    public void deleteRun(String name, String userEmail, String userPassword) {
        Optional<User> user = userService.getUserByEmailAndPassword(userEmail, userPassword);

        if(user.isPresent()) {
            runRepository.deleteRunByNameAndUserEmail(name, userEmail);
        }
    }
}
