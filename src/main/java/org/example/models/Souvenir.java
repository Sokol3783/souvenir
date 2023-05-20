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
       return  String.format("%3s %-45s %-16s %-10s %-13s %5.2f",
        id, name, brand, owner.getName(), dateIssue, price);

    }
}
