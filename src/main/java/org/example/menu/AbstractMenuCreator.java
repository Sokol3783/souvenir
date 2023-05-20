package org.example.menu;

import org.example.dao.DAO;
import org.example.dao.FabricatorDAO;
import org.example.dao.SouvenirDAO;
import org.example.menu.Menu.Item;
import org.example.menu.Menu.SortedMenu;
import org.example.task.TaskItems;
import org.example.utils.Chooser;
import org.example.utils.Chooser.FabricatorChooser;
import org.example.utils.Chooser.SouvenirChooser;


public abstract class AbstractMenuCreator {

  /**
   * Create default menu of inner classes
   */
  public abstract Menu getMenu();

  public static class MainMenuCreator extends AbstractMenuCreator {

    private final TaskItems taskItem;

    public MainMenuCreator(TaskItems taskItem) {
      this.taskItem = taskItem;
    }

    @Override
    public Menu getMenu() {
      SortedMenu<Item> items = new SortedMenu<>(Menu.SortedMenu.defaultComparator());
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
      return new Item(9, "Remove fabricator and fabricator's souvenirs from menu",
          taskItem::removeFabricatorAndFabricatorsSouvenir);
    }

    private Item listSouvenirsByYear() {
      return new Item(8, "List souvenirs by year", taskItem::listSouvenirsByYear);
    }

    private Item listInfoAboutSouvenirFabricatorsByYear() {
      return new Item(7,
          "List information about all fabricators which create souvenir in chosen year",
          taskItem.listInfoAboutSouvenirFabricatorsByYear());
    }

    private Item listFabricatorsAndListFabricatorsSouvenirs() {
      return new Item(6, "List all fabricators and their souvenirs",
          taskItem.listFabricatorsAndListFabricatorsSouvenirs());
    }

    private Item listFabricatorsHasPriceLessThenInputPrice() {
      return new Item(5, "List all fabricators that has price less then $0",
          taskItem.listFabricatorsHasPriceLessThenInputPrice());
    }

    private Item listSouvenirsFromCountry() {

      return new Item(4, "List souvenirs from country", () -> taskItem.listSouvenirsFromCountry());
    }

    private Item listFabricatorsSouvenirsByInput() {
      return new Item(3, "List fabricator souvenirs by $fabricator",
          taskItem::listFabricatorsSouvenirsByInput);
    }

    private Item souvenirMenu() {
      return new Item(2, "Souvenir menu", () -> {
        Menu menu = new ModelMenuCreator(SouvenirDAO.getInstance(),
            new SouvenirChooser(SouvenirDAO.getInstance())).getMenu();
        menu.runMenu();
      });
    }

    private Item fabricatorMenu() {
      return new Item(1, "Fabricator menu", () -> {
        Menu menu = new ModelMenuCreator(FabricatorDAO.getInstance(),
            new FabricatorChooser(FabricatorDAO.getInstance())).getMenu();
        menu.runMenu();
      });
    }

  }

  public static class ModelMenuCreator<T> extends AbstractMenuCreator {

    private final DAO<T> dao;
    private final Chooser<T> chooser;

    public ModelMenuCreator(DAO<T> dao, Chooser<T> chooser) {
      this.dao = dao;
      this.chooser = chooser;
    }

    @Override
    public Menu getMenu() {
      SortedMenu<Item> items = new SortedMenu<>(Menu.SortedMenu.defaultComparator());
      items.add(new Item(1, "add", dao::create));
      items.add(new Item(2, "change", () -> dao.update(chooser.choose())));
      items.add(new Item(3, "list all", () -> dao.getAll().forEach(System.out::println)));
      return new Menu(items);
    }
  }
}
