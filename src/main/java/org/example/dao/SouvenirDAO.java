package org.example.dao;

import java.util.Optional;
import org.example.models.Souvenir;

public class SouvenirDAO<T extends Souvenir> extends AbstractDAO<T>{

  private static SouvenirDAO dao;
  
  private SouvenirDAO() {
  }

  @Override
  public Optional<T> create() {
    return Optional.empty();
  }

  public static DAO getInstance() {
    if (dao == null) {      
      dao = new SouvenirDAO<>();
    }    
    return dao;
  }

}
