package org.example.dao;

import java.util.Optional;
import org.example.models.Fabricator;

public class FabricatorDAO<T extends Fabricator> extends AbstractDAO<T> {
  private static FabricatorDAO dao;
  
  private FabricatorDAO() {
  }

  @Override
  public Optional<T> create() {
    return Optional.empty();
  }

  public static DAO getInstance() {
    if (dao == null) {
      dao = new FabricatorDAO<>();
    }
    return dao;
  }


}
