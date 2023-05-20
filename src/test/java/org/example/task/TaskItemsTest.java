package org.example.task;

import java.util.List;
import org.example.dao.DAO;
import org.example.dao.FabricatorDAO;
import org.example.dao.SouvenirDAO;
import org.example.models.Fabricator;
import org.example.models.Souvenir;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskItemsTest {

  private static final DAO<Souvenir> souvenirDAO = SouvenirDAO.getInstance();
  private static final DAO<Fabricator> fabricatorDAO = FabricatorDAO.getInstance();

  @BeforeEach
  void setUp() { 
    List<Fabricator> fabricators = getFabricators();
    fabricators.forEach(fabricatorDAO::update);

    List<Souvenir> souvenirs = getSouvenirs();
    souvenirs.forEach(souvenirDAO::update);
  }

  private List<Souvenir> getSouvenirs(){ 
    return List.of();
  }

  private List<Fabricator> getFabricators(){ 
    return List.of();
  }

  @AfterEach
  void tearDownTest() { 
    fabricatorDAO.getAll().forEach(fabricatorDAO::delete);
    souvenirDAO.getAll().forEach(souvenirDAO::delete);
  }

  @Test
  void listFabricatorsSouvenirsByInputTest() { 
  }

  @Test
  void listSouvenirsFromCountryTest() { 
  }

  @Test
  void listFabricatorsHasPriceLessThenInputPriceTest() { 
  }

  @Test
  void listFabricatorsAndListFabricatorsSouvenirsTest() { 
  }

  @Test
  void listInfoAboutSouvenirFabricatorsByYearTest() { 
  }

  @Test
  void listSouvenirsByYearTest() { 
  }

  @Test
  void removeFabricatorAndFabricatorsSouvenirTest() { 
  }
}