package org.example.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.example.models.DefaultFields;

public abstract class AbstractDAO<T extends DefaultFields> implements DAO<T>{
  
  private final Collection<T> models;

  public AbstractDAO() {
    this.models = new HashSet<>();
  }

  @Override
  public Optional<T> get(Short id) {
    return models.stream().filter(s -> s.getId() == id).findFirst();
  }

  @Override
  public List<T> getAll() {
    return List.copyOf(models);
  }


  @Override
  public Optional<T> save(T model) {
    return addToCollections(model);
  }

  @Override
  public Optional<T> update(T model) {
    return addToCollections(model);
  }

  @Override
  public void delete(T model) {
    models.remove(model);
  }


  private Optional<T>  addToCollections(T model){
    if (models.add(model)) {
      return Optional.of(model);
    }
    return Optional.empty();
  }

}
