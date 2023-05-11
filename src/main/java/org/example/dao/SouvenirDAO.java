package org.example.dao;

import org.example.models.Souvenir;

public class SouvenirDAO<T extends Souvenir> extends AbstractDAO<T>{

  private static SouvenirDAO dao;
  
  private SouvenirDAO() {
  }
  
  public static DAO getInstance() {
    if (dao == null) {      
      dao = new SouvenirDAO<>();
    }    
    return dao;
  }

}
