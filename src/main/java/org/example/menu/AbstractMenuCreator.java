package org.example.menu;

import org.example.Util;
import org.example.dao.DAO;
import org.example.dao.FabricatorDAO;
import org.example.dao.SouvenirDAO;
import org.example.menu.Menu.Item;
import org.example.menu.Menu.SortedMenuList;
import org.example.models.Fabricator;
import org.example.models.Souvenir;


public abstract class AbstractMenuCreator {

  /**
   * Create default menu of inner classes
   */
  public abstract Menu getMenu();

  public static class MainMenuCreator extends AbstractMenuCreator {

    DAO<Souvenir> souvenirDAO = SouvenirDAO.getInstance();
    DAO<Fabricator> fabricatorDAO = FabricatorDAO.getInstance();

    @Override
    public Menu getMenu() {
      SortedMenuList<Item> items = new SortedMenuList<>();
      //Додавати, редагувати, переглядати всіх виробників
      items.add(fabricatorMenu());
      //Додавати, редагувати, переглядати всіх сувеніри.
      items.add(souvenirMenu());
      //Вивести інформацію про сувеніри заданого виробника.
      items.add(listFabricatorsSouvenirsByInput());
      //Вивести інформацію про сувеніри, виготовлені в заданій країні.
      items.add(listSouvenirsFromCountry());
      //Вивести інформацію про виробників, чиї ціни на сувеніри менше заданої.
      items.add(listFabricatorsLessThenInputPrice());
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
        Fabricator remove = Util.enterFabricator();
        souvenirDAO.getAll().stream().filter(s -> s.getOwner().equals(remove))
            .forEach(souvenirDAO::delete);
        fabricatorDAO.delete(remove);
        System.out.println("Fabricator and souvenirs has been removed");
      });
    }

    private Item listSouvenirsByYear() {
      return new Item(8, "List souvenirs by year", () -> {
        int year = Integer.parseInt(Util.enterValue("year"));
        souvenirDAO.getAll().stream().filter(s -> s.getDateIssue().getYear() == year)
            .forEach(System.out::println);
      });
    }

    private Item listInfoAboutSouvenirFabricatorsByYear() {
      return new Item(7,
          "List information about all fabricators which create souvenir in chosen year", () -> {
        Souvenir souvenir = Util.enterSouvenir();
        int year = Integer.parseInt(Util.enterValue("year"));
        souvenirDAO.getAll().stream().filter(
                s -> s.getName().contains(souvenir.getName()) && s.getDateIssue().getYear() == year)
            .map(Souvenir::getOwner).distinct().forEach(System.out::println);
      });
    }

    private Item listFabricatorsAndListFabricatorsSouvenirs() {
      Item item = null;
      return item;
    }

    private Item listFabricatorsLessThenInputPrice() {
      Item item = null;
      return item;
    }

    private Item listSouvenirsFromCountry() {
      Item item = null;
      return item;
    }

    private Item listFabricatorsSouvenirsByInput() {
      Item item = null;
      return item;
    }

    private Item souvenirMenu() {
      return null;
    }

    private Item fabricatorMenu() {
      Item item = null;
      return item;
    }


  }

}
