package org.example.menu;

public abstract class AbstractMenu {

  public abstract Menu getMenu();

  public static class MainMenu extends AbstractMenu{


    @Override
    public Menu getMenu() {
      Menu menu = new Menu();

      return null;
    }
  }

}
