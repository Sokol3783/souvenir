package org.example.menu;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.Util;
import org.example.dao.DAO;
import org.example.dao.FabricatorDAO;
import org.example.dao.SouvenirDAO;
import org.example.menu.Menu.Item;
import org.example.menu.Menu.SortedMenu;
import org.example.models.Fabricator;
import org.example.models.Souvenir;


public abstract class AbstractMenuCreator {

  DAO<Souvenir> souvenirDAO = SouvenirDAO.getInstance();
  DAO<Fabricator> fabricatorDAO = FabricatorDAO.getInstance();

  /**
   * Create default menu of inner classes
   */
  public abstract Menu getMenu();

  //TODO
  private static Fabricator chooseFabricator() {
    return null;
  }

  //TODO
  private static Souvenir chooseSouvenir() {
    return null;
  }

  public static class MainMenuCreator extends AbstractMenuCreator {

    @Override
    public Menu getMenu() {
      SortedMenu<Item> items = new SortedMenu<>();
      //Додавати, редагувати, переглядати всіх виробників
      items.add(fabricatorMenu());
      //Додавати, редагувати, переглядати всіх сувеніри.
      items.add(souvenirMenu());
      //Вивести інформацію про сувеніри заданого виробника.
      items.add(listFabricatorsSouvenirsByInput());
      //Вивести інформацію про сувеніри, виготовлені в заданій країні.
      items.add(listSouvenirsFromCountry());
      //Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої.
      items.add(listFabricatorsHasPriceLessThenInputPrice());
      //Вивести інформацію по всіх виробниках та, для кожного виробника вивести інформацію про всі сувеніри, які він виробляє.
      items.add(listFabricatorsAndListFabricatorsSouvenirs());
      //Вивести інформацію про виробників заданого сувеніру, виробленого у заданому року.
      items.add(listInfoAboutSouvenirFabricatorsByYear());
      //Для кожного року вивести список сувенірів, зроблених цього року.
      items.add(listSouvenirsByYear());
      //Видалити заданого виробника та його сувеніри.
      items.add(removeFabricatorAndFabricatorsSouvenir());
      return new Menu(items);
    }

    private Item removeFabricatorAndFabricatorsSouvenir() {
      return new Item(9, "Remove fabricator and fabricators from menu", () -> {
        Fabricator remove = chooseFabricator();
        souvenirDAO.getAll().stream().filter(s -> s.getOwner().equals(remove))
            .forEach(souvenirDAO::delete);
        fabricatorDAO.delete(remove);
        System.out.println("Fabricator and souvenirs has been removed");
      });
    }

    private Item listSouvenirsByYear() {
      return new Item(8, "List souvenirs by year", () -> {
        int year = Util.enterIntValue("year");
        souvenirDAO.getAll().stream().filter(s -> s.getDateIssue().getYear() == year)
            .forEach(System.out::println);
      });
    }

    private Item listInfoAboutSouvenirFabricatorsByYear() {
      return new Item(7,
          "List information about all fabricators which create souvenir in chosen year", () -> {
        Souvenir souvenir = chooseSouvenir();
        int year = Util.enterIntValue("year");
        souvenirDAO.getAll().stream().filter(
                s -> s.getName().contains(souvenir.getName()) && s.getDateIssue().getYear() == year)
            .map(Souvenir::getOwner).distinct().forEach(System.out::println);
      });
    }

    private Item listFabricatorsAndListFabricatorsSouvenirs() {
      return new Item(6, "List all fabricators and their souvenirs", () -> {
        Map<Fabricator, List<Souvenir>> collect = souvenirDAO.getAll().stream()
            .collect(Collectors.groupingBy(Souvenir::getOwner));
        collect.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey().getName()))
            .forEach(e -> {
              System.out.println(" - " + e.getKey());
              e.getValue().stream()
                  .sorted(Comparator.comparing(Souvenir::getBrand).thenComparing(Souvenir::getName))
                  .forEach(s -> System.out.println(" - " + s));
            });
      });
    }

    private Item listFabricatorsHasPriceLessThenInputPrice() {
      return new Item(5, "List all fabricators that has price less then $0", () -> {
        int price = Util.enterIntValue("price $");
        souvenirDAO.getAll().stream().filter(s -> s.getPrice() < price).
            map(Souvenir::getOwner).distinct().sorted().forEach(
                System.out::println);
      });
    }

    private Item listSouvenirsFromCountry() {
      String country = Util.enterStringValue("country");
      return new Item(4, "List souvenirs from country", () -> souvenirDAO.getAll().stream()
          .filter(s -> s.getOwner().getCountry().compareToIgnoreCase(country) == 0).forEach(
              System.out::println));
    }

    private Item listFabricatorsSouvenirsByInput() {
      return new Item(3, "List fabricator souvenirs by $fabricator", () -> {
        Fabricator fabricator = chooseFabricator();
        souvenirDAO.getAll().stream().filter(s -> s.getOwner().equals(fabricator)).sorted().forEach(
            System.out::println);
      });
    }

    private Item souvenirMenu() {
      return new Item(2, "souvenir menu", () -> {
        Menu menu = new SouvenirMenuCreator().getMenu();
        menu.runMenu();
      });
    }

    private Item fabricatorMenu() {
      return new Item(1, "fabricator menu", () -> {
        Menu menu = new FabricatorMenuCreator().getMenu();
        menu.runMenu();
      });
    }

  }

  public static class SouvenirMenuCreator extends AbstractMenuCreator {

    @Override
    public Menu getMenu() {
      SortedMenu<Item> items = new SortedMenu<>();
      items.add(new Item(1, "add", () ->
          souvenirDAO.create()));
      items.add(new Item(2, "change", () ->
          souvenirDAO.update(chooseSouvenir())));
      items.add(new Item(3, "list all", () ->
          souvenirDAO.getAll().forEach(System.out::println)));
      return new Menu(items);
    }
  }

  public static class FabricatorMenuCreator extends AbstractMenuCreator {

    @Override
    public Menu getMenu() {
      SortedMenu<Item> items = new SortedMenu<>();
      items.add(new Item(1, "add", () ->
          fabricatorDAO.create()));
      items.add(new Item(2, "change", () ->
          fabricatorDAO.update(chooseFabricator())));
      items.add(new Item(3, "list all", () ->
          fabricatorDAO.getAll().forEach(System.out::println)));
      return new Menu(items);
    }
  }
}
