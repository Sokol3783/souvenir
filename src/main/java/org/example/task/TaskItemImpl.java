package org.example.task;

import static org.example.utils.Util.chooseFabricator;
import static org.example.utils.Util.chooseSouvenir;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.utils.Util;
import org.example.dao.DAO;
import org.example.models.Fabricator;
import org.example.models.Souvenir;

public class TaskItemImpl implements TaskItems {

  private final DAO<Souvenir> souvenirDAO;
  private final DAO<Fabricator> fabricatorDAO;

  public TaskItemImpl(DAO<Souvenir> souvenirDAO, DAO<Fabricator> fabricatorDAO) {
    this.souvenirDAO = souvenirDAO;
    this.fabricatorDAO = fabricatorDAO;
  }

  @Override
  public Runnable listFabricatorsSouvenirsByInput() {
    return new Runnable() {
      @Override
      public void run() {
        Fabricator fabricator = chooseFabricator();
        souvenirDAO.getAll().stream().filter(s -> s.getOwner().equals(fabricator)).sorted().forEach(
            System.out::println);
      }
    };
  }

  @Override
  public Runnable listSouvenirsFromCountry() {
    return new Runnable() {
      @Override
      public void run() {
        String country = Util.enterStringValue("country");
        souvenirDAO.getAll().stream()
            .filter(s -> s.getOwner().getCountry().compareToIgnoreCase(country) == 0).forEach(
                System.out::println);
      }
    };
  }

  @Override
  public Runnable listFabricatorsHasPriceLessThenInputPrice() {
    return new Runnable() {
      @Override
      public void run() {
        int price = Util.enterIntValue("price $");
        souvenirDAO.getAll().stream().filter(s -> s.getPrice() < price).
            map(Souvenir::getOwner).distinct().sorted().forEach(
                System.out::println);
      }
    };
  }

  @Override
  public Runnable listFabricatorsAndListFabricatorsSouvenirs() {
    return new Runnable() {
      @Override
      public void run() {
        Map<Fabricator, List<Souvenir>> collect = souvenirDAO.getAll().stream()
            .collect(Collectors.groupingBy(Souvenir::getOwner));
        collect.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey().getName()))
            .forEach(e -> {
              System.out.println(" - " + e.getKey());
              e.getValue().stream()
                  .sorted(Comparator.comparing(Souvenir::getBrand).thenComparing(Souvenir::getName))
                  .forEach(s -> System.out.println(" - " + s));
            });
      }
    };
  }

  @Override
  public Runnable listInfoAboutSouvenirFabricatorsByYear() {
    return new Runnable() {
      @Override
      public void run() {
        Souvenir souvenir = chooseSouvenir();
        int year = Util.enterIntValue("year");
        souvenirDAO.getAll().stream().filter(
                s -> s.getName().contains(souvenir.getName()) && s.getDateIssue().getYear() == year)
            .map(Souvenir::getOwner).distinct().forEach(System.out::println);
      }
    };
  }

  @Override
  public Runnable listSouvenirsByYear() {
    return new Runnable() {
      @Override
      public void run() {
        int year = Util.enterIntValue("year");
        souvenirDAO.getAll().stream().filter(s -> s.getDateIssue().getYear() == year)
            .forEach(System.out::println);
      }
    };
  }

  @Override
  public Runnable removeFabricatorAndFabricatorsSouvenir() {
    return new Runnable() {
      @Override
      public void run() {
        Fabricator remove = chooseFabricator();
        souvenirDAO.getAll().stream().filter(s -> s.getOwner().equals(remove))
            .forEach(souvenirDAO::delete);
        fabricatorDAO.delete(remove);
        System.out.println("Fabricator and souvenirs has been removed");
      }
    };
  }
}
