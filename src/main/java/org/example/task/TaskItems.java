package org.example.task;

public interface TaskItems {
  Runnable listFabricatorsSouvenirsByInput();
  Runnable listSouvenirsFromCountry();

  Runnable listFabricatorsHasPriceLessThenInputPrice();

  Runnable listFabricatorsAndListFabricatorsSouvenirs();

  Runnable listInfoAboutSouvenirFabricatorsByYear();

  Runnable listSouvenirsByYear();

  Runnable removeFabricatorAndFabricatorsSouvenir();

}
