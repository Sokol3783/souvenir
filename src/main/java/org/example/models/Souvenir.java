package org.example.models;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Souvenir extends DefaultFields {
    short id;
    String name;
    String brand; //реквізити виробника
    LocalDate dateIssue;
    int price;
    Fabricator owner;

    @Override
    public String toString(){
       return  String.format("%5s %-25.25s %-15.15s %s %.2f",
        id, name, brand, dateIssue, (double) price /100);

    }
}
