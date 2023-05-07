package org.example.dao;

import java.util.List;
import java.util.Optional;
import org.example.models.DefaultFields;

public abstract class AbstractDAO<T extends DefaultFields> implements DAO{
  
  private final List<T> models;

  public AbstractDAO(List<T> models) {
    this.models = models;
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
  public Optional save(Object model) {
    return save(T model);
  }

  @Override
  public Optional update(Object model) {
    return Optional.empty();
  }

  @Override
  public void delete(Object model) {

  }


  private Optional<T> save(T model) {
    if (models.add(model)) {
      return Optional.of(model);
    }
    return Optional.empty();
  }


  public Optional<T> update(T model) {
    boolean b = models.removeIf(s -> s.getId() == model.getId());
    if (b){
      models.add(model);
      return Optional.of(model);
    }
    return Optional.empty();
  }


  public void delete(T model) {
    models.removeIf(s -> s.equals(model));
  }
  
}
