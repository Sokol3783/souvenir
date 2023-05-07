package org.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.models.Fabricator;

public class FabricatorDAO<T extends Fabricator> extends AbstractDAO<T> implements DAO<T> {

  private final List<T> fabricators;
  private static FabricatorDAO dao;
  
  private FabricatorDAO() {
    fabricators = new ArrayList<>()
    super(fabricators);

  }
  
  public static DAO getInstance() {
    if (dao == null) {
      dao = new FabricatorDAO<>();
    }
    return dao;
  }

}
