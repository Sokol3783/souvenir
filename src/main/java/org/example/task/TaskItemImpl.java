package org.example.task;

import static org.example.utils.Util.chooseSouvenir;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.dao.DAO;
import org.example.models.Fabricator;
import org.example.models.Souvenir;
import org.example.utils.Chooser;
import org.example.utils.Chooser.FabricatorChooser;
import org.example.utils.Chooser.SouvenirChooser;
import org.example.utils.Util;

public class TaskItemImpl implements TaskItems {

  private final DAO<Souvenir> souvenirDAO;
  private final DAO<Fabricator> fabricatorDAO;

  private final Chooser<Fabricator> chooserFab;
  private final Chooser<Souvenir> chooserSouvenir;

  public TaskItemImpl(DAO<Souvenir> souvenirDAO, DAO<Fabricator> fabricatorDAO) {
    this.souvenirDAO = souvenirDAO;
    this.fabricatorDAO = fabricatorDAO;
    this.chooserFab = new FabricatorChooser<>(fabricatorDAO);
    this.chooserSouvenir = new SouvenirChooser<>(souvenirDAO);
  }

  @Override
  public Runnable listFabricatorsSouvenirsByInput() {
    return new Runnable() {
      @Override
      public void run() {
        Fabricator fabricator = chooserFab.choose();
        printSouvenirHeader();
        souvenirDAO.getAll().stream().filter(s -> s.getOwner().equals(fabricator))
            .sorted(Comparator.comparing(Souvenir::getId))
            .forEach(System.out::println);
      }
    };
  }

  private void printSouvenirHeader() {
    System.out.println("List:");
    System.out.printf("%3s %-21s %-20s %12s %-3s %s %-12s %10s %8s\n",
        "id","", "name","brand", "", "","fabricator","date issue","price");
  }

  @Override
  public Runnable listSouvenirsFromCountry() {
    return new Runnable() {
      @Override
      public void run() {
        String country = Util.enterStringValue("country");
        printSouvenirHeader();
        souvenirDAO.getAll().stream()
            .filter(s -> s.getOwner().getCountry().compareToIgnoreCase(country) == 0)
            .sorted(Comparator.comparing(Souvenir::getId))
            .forEach(System.out::println);
      }
    };
  }

  @Override
  public Runnable listFabricatorsHasPriceLessThenInputPrice() {
    return new Runnable() {
      @Override
      public void run() {
        int price = Util.enterIntValue("price $");
        System.out.println("List:");
        souvenirDAO.getAll().stream().filter(s -> s.getPrice() < price).
            map(Souvenir::getOwner).distinct().sorted(Comparator.comparing(Fabricator::getId)).forEach(
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
        printSouvenirHeader();
        souvenirDAO.getAll().stream().filter(s -> s.getDateIssue().getYear() == year)
            .sorted(Comparator.comparing(Souvenir::getId)).forEach(System.out::println);
      }
    };
  }

  @Override
  public Runnable removeFabricatorAndFabricatorsSouvenir() {
    return new Runnable() {
      @Override
      public void run() {
        Fabricator remove = chooserFab.choose();
        souvenirDAO.getAll().stream().filter(s -> s.getOwner().equals(remove))
            .forEach(souvenirDAO::delete);
        fabricatorDAO.delete(remove);
        System.out.println("Fabricator and souvenirs has been removed");
      }
    };
  }
}
