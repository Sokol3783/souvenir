package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Fabricator implements DefaultFields {
  short id;
  String name;
  String country;
}
