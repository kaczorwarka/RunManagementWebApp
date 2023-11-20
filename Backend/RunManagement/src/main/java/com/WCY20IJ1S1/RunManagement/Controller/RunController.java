package com.WCY20IJ1S1.RunManagement.Controller;

import com.WCY20IJ1S1.RunManagement.Model.Run;
import com.WCY20IJ1S1.RunManagement.Service.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/DB/Runs")
public class RunController {

    @Autowired
    private RunService runService;

    @GetMapping("/get/{name}")
    public ResponseEntity<Optional<Run>> getRun(@PathVariable String name){
        return new ResponseEntity<Optional<Run>>(runService.getRun(name), HttpStatus.OK);
    }

    @GetMapping("/get/all/{email}/{password}")
    public ResponseEntity<List<Run>> getUserRuns(@PathVariable String email, @PathVariable String password){
        List<Run> runs = runService.getRunsByUserEmailAndPassword(email, password);
        return new ResponseEntity<List<Run>>(runs, HttpStatus.OK);
    }
    @GetMapping("/get/future/{email}/{password}")
    public ResponseEntity<List<Run>> getFutureUserRuns(@PathVariable String email, @PathVariable String password){
        List<Run> runs = runService.getFutureRunsByUserEmailAndPassword(email, password);
        return new ResponseEntity<List<Run>>(runs, HttpStatus.OK);
    }

    @GetMapping("/get/past/{email}/{password}")
    public ResponseEntity<List<Run>> getPastUserRuns(@PathVariable String email, @PathVariable String password){
        List<Run> runs = runService.getPastRunsByUserEmailAndPassword(email, password);
        return new ResponseEntity<List<Run>>(runs, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Run> addRun(@RequestBody Map<String, String> payload){
        Run run = runService.addRun(
                payload.get("name"),
                payload.get("distance"),
                payload.get("date"),
                payload.get("localization"),
                payload.get("website"),
                payload.get("type"),
                payload.get("time"),
                payload.get("rankingPlace"),
                payload.get("userEmail"));

        return new ResponseEntity<Run>(run, (run != null) ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    @PutMapping("/put/{oldName}/{userEmail}/{userPassword}")
    public ResponseEntity<Run> updateRun(@PathVariable String  oldName,
                                         @PathVariable String userEmail,
                                         @PathVariable String userPassword,
                                         @RequestBody Map<String, String> payload){
        return new ResponseEntity<Run>(
                runService.updateRunData(
                        oldName,
                        payload.get("name"),
                        payload.get("distance"),
                        payload.get("date"),
                        payload.get("localization"),
                        payload.get("website"),
                        payload.get("type"),
                        payload.get("time"),
                        payload.get("rankingPlace"),
                        userEmail,
                        userPassword
                ), HttpStatus.OK);
    }
    @DeleteMapping("delete/{name}/{userEmail}/{userPassword}")
    public void deleteRun(@PathVariable String name,
                          @PathVariable String userEmail,
                          @PathVariable String userPassword){
        runService.deleteRun(name, userEmail, userPassword);
    }
}
