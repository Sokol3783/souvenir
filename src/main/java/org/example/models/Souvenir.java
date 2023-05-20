package org.example.models;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class Souvenir extends DefaultFields {
    int id;
    String name;
    String brand; //реквізити виробника
    LocalDate dateIssue;
    double price;
    Fabricator owner;

    @Override
    public String toString(){
       return  String.format("%5s %-25.25s %-15.15s %25s %15s %-f",
        id, name, brand, owner.getName(), dateIssue, price);

    }
}
