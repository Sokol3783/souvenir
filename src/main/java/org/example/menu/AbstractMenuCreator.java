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
    DAO<Fabricator> fabricatorDAO= FabricatorDAO.getInstance();

    @Override
    public Menu getMenu() {
      SortedMenuList<Item> items = new SortedMenuList<>();
      items.add(FabricatorMenu());
      items.add(SouverMenu());
      items.add(listFabricatorsSouvenirsByInput());
      items.add(listSouvenirsFromCountry());
      items.add(listFabricatorsLessThenInputPrice());
      items.add(listFabricatorsAndListFabricatorsSouvenirs());
      items.add(listInfoAboutSouvenirFabricators());
      items.add(listSouvenirsByYear());
      items.add(removeFabricatorAndFabricatorsSouvenir());
      Menu menu = new Menu(items);
      return menu;
    }

    private Item removeFabricatorAndFabricatorsSouvenir() {
      return new Item(
          9, "Remove fabricator and fabricators from menu", new Runnable(){

        @Override
        public void run() {
          Fabricator remove = Util.enterFabricator();
          souvenirDAO.getAll().stream().filter(s -> s.getOwner().equals(remove)).forEach(souvenirDAO::delete);
          fabricatorDAO.delete(remove);
          System.out.println("Fabricator and souvenirs has been removed");
        }
      }
      );
    }

    private Item listSouvenirsByYear() {
      return new Item(8, "List souvenirs by year", new Runnable() {
        @Override
        public void run() {
          int year = Integer.parseInt(Util.enterValue());
          souvenirDAO.getAll().stream().filter(s -> s.getDateIssue().getYear() == year).forEach(
              System.out::println);
        }
      });
    }

    private Item listInfoAboutSouvenirFabricators() {
      return null;
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

    private Item SouverMenu() {
      return null;
    }

    private Item FabricatorMenu() {
      Item item = null;
      return item;
    }


  }

}
