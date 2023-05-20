package org.example;

import java.util.Scanner;
import org.example.dao.FabricatorDAO;
import org.example.dao.SouvenirDAO;
import org.example.menu.AbstractMenuCreator.MainMenuCreator;
import org.example.menu.Menu;
import org.example.task.TaskItemImpl;
import org.example.utils.Util;

public class App {

  public final static Scanner IN = new Scanner(System.in);
  public static String path = "/data/";

  public static void main(String[] args) {
    Util.restoreData();
    Menu menu = new MainMenuCreator(new TaskItemImpl(SouvenirDAO.getInstance(),
        FabricatorDAO.getInstance())).getMenu();
    menu.runMenu();
    Util.saveData();
  }
}
