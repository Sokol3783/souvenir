package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
  
  Optional<T> get(Short id);
  
  List<T> getAll();
  
 Optional<T> save(T model);
  
 Optional<T> update(T model);
    
 void delete(T model);
  
}
