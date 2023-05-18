package org.example;

import java.util.Scanner;
import org.example.menu.AbstractMenuCreator;
import org.example.menu.AbstractMenuCreator.MainMenuCreator;
import org.example.menu.Menu;

public class App {

  public final static Scanner IN = new Scanner(System.in);
  public static String path = "/data/";

  public static void main(String[] args) {
      Util.restoreData();
      Menu menu = new MainMenuCreator().getMenu();
      menu.runMenu();
      Util.saveData();
  }
}
