package org.example.dao;

import org.example.models.Fabricator;

public class FabricatorDAO<T extends Fabricator> extends AbstractDAO<T> {
  private static FabricatorDAO dao;
  
  private FabricatorDAO() {
  }
  
  public static DAO getInstance() {
    if (dao == null) {
      dao = new FabricatorDAO<>();
    }
    return dao;
  }


}
