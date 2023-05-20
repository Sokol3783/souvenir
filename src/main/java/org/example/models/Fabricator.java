package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@Data
@AllArgsConstructor
public class Fabricator extends DefaultFields {
  int id;
  String name;
  String country;

}
