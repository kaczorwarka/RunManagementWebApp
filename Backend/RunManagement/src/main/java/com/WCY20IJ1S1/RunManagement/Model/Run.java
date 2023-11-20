package com.WCY20IJ1S1.RunManagement.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Date;

@Document(collection = "Runs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes(
        @CompoundIndex(name = "test", def = "{'userEmail': 1, 'name': 1}", unique = true)
)
public class Run {
    @Id
    private ObjectId id;
    private String name;
    private String distance;
    private Date date;
    private String localization;
    private String website;
    private String type;
    private String time;
    private Integer rankingPlace;
    private String userEmail;

    public Run(String name, String distance, Date date, String localization,
               String website, String type, String time, Integer rankingPlace,
               String userEmail) {
        this.name = name;
        this.distance = distance;
        this.date = date;
        this.localization = localization;
        this.website = website;
        this.type = type;
        this.time = time;
        this.rankingPlace = rankingPlace;
        this.userEmail = userEmail;
    }
}
