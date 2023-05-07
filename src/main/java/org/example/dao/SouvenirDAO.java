package org.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.models.Fabricator;
import org.example.models.Souvenir;

public class SouvenirDAO<T extends Souvenir> implements DAO<T> {

  private final List<T> souvenirs; 
  private static SouvenirDAO dao;
  
  private SouvenirDAO() {
    souvenirs = new ArrayList<>();
  }
  
  public static DAO getInstance() {
    if (dao == null) {      
      dao = new SouvenirDAO<>();
    }    
    return dao;
  }

  @Override
  public Optional<T> get(Short id) {
    return souvenirs.stream().filter(s -> s.getId() == id).findFirst();
  }

  @Override
  public List<T> getAll() {
    return List.copyOf(souvenirs);
  }

  @Override
  public Optional<T> save(T model) {
    if (souvenirs.add(model)) {
      return Optional.of(model);
    }
    return Optional.empty();
  }

  @Override
  public Optional<T> update(T model) {
    boolean b = souvenirs.removeIf(s -> s.getId() == model.getId());
    if (b){
      souvenirs.add(model);
      return Optional.of(model);
    }
    return Optional.empty();
  }

  @Override
  public void delete(T model) {
    souvenirs.removeIf(s -> s.equals(model));
  }

}
