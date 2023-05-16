package org.example.menu;

import java.util.Comparator;
import org.example.menu.Menu.Item;
import org.example.menu.Menu.SortedMenuList;

public abstract class AbstractMenu {

  /**
   * Create default menu of inner classes
   */
  public abstract Menu getMenu();

  public abstract Menu fillMenu(Menu menu);

  public static class MainMenu extends AbstractMenu{

    @Override
    public Menu getMenu() {
      SortedMenuList<Item> items = new SortedMenuList<>();
      Menu menu = new Menu(null);
      return menu;
    }

    @Override
    public Menu fillMenu(Menu menu) {

      return menu;
    }

  }

}
