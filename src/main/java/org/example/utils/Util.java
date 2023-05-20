package org.example.utils;

import static org.example.App.IN;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.example.App;
import org.example.dao.DAO;
import org.example.dao.FabricatorDAO;
import org.example.dao.SouvenirDAO;
import org.example.models.Fabricator;
import org.example.models.Souvenir;
import org.jetbrains.annotations.NotNull;


public class Util {

  @NotNull
  public static String enterStringValue(String nameValue) {
    printLine(nameValue);
    while (IN.hasNext()) {

      if (IN.hasNextLine()) {
        String value = IN.nextLine();
        if (!value.isEmpty()) {
          return value;
        }
      }

      printMessageAndLine(nameValue, "Invalid input, please repeat");
    }
    return "";
  }

  public static int enterIntValue(String nameValue) {
    printLine(nameValue);
    while (IN.hasNext()) {
      if (IN.hasNextInt()) {
        return IN.nextInt();
      }
      printMessageAndLine(nameValue, "Invalid input, please repeat");
    }
    return 0;
  }

  private static void printLine(String nameValue) {
    System.out.println("Enter the " + nameValue + ":");
  }

  private static void printMessageAndLine(String nameValue, String message) {
    System.out.println(message);
    printLine(nameValue);
  }


  public static void restoreData() {
    try {
      restoreFabricator();
      restoreSouvenirs();
    } catch (FileNotFoundException e) {
    }
  }

  private static void restoreSouvenirs() throws FileNotFoundException {
    Gson gson = new Gson();
    List<Souvenir> coll = gson.fromJson(
        new FileReader(App.path + Souvenir.class.getCanonicalName()),
        new TypeToken<List<Souvenir>>() {
        }.getType());
    DAO<Souvenir> dao = SouvenirDAO.getInstance();
    coll.forEach(dao::update);
  }

  private static void restoreFabricator() throws FileNotFoundException {
    Gson gson = new Gson();
    List<Fabricator> coll = gson.fromJson(
        new FileReader(App.path + Fabricator.class.getCanonicalName()),
        new TypeToken<List<Fabricator>>() {
        }.getType());
    DAO<Fabricator> dao = FabricatorDAO.getInstance();
    coll.forEach(dao::update);
  }

  public static void saveData() {
    try {
      saveSouvenirs();
    } catch (IOException e) {
      System.out.println("Souvenirs hasn't been saved");
    }

    try {
      saveFabricator();
    } catch (IOException e) {
      System.out.println("Fabricators hasn't been saved");
    }
  }

  private static void saveSouvenirs() throws IOException {
    Gson gson = new Gson();
    List all = SouvenirDAO.getInstance().getAll();
    String serialized = gson.toJson(all);
    try (FileWriter writer = new FileWriter(App.path + Souvenir.class.getCanonicalName())) {
      writer.write(serialized);
    }
  }

  private static void saveFabricator() throws IOException {
    Gson gson = new Gson();
    List all = FabricatorDAO.getInstance().getAll();
    String serialized = gson.toJson(all);
    try (FileWriter writer = new FileWriter(App.path + Fabricator.class.getCanonicalName())) {
      writer.write(serialized);
    }
  }

  //TODO
  public static Fabricator chooseFabricator() {
    System.out.println("""
        1. Find by name.
        2. Find by id.
        Enter value number: 
        """);
    while (IN.hasNextLine()) {
      if (IN.hasNextInt()) {
        switch (IN.nextInt()) {
          case 1 -> {
            return findFabricatorByName();
          }
          case 2 -> {
            return findFabricatorById();
          }
          default -> System.out.println("Invalid input, enter '1' or '2'");
        }
      }
    }
    return null;
  }

  public static Fabricator findFabricatorById() {
    Optional<Fabricator> first = Optional.empty();
    while (first.isEmpty()) {
      //TODO
      //first = fabricatorDAO.get(Util.enterIntValue("id"));
    }
    return first.get();
  }

  private static Fabricator findFabricatorByName() {
    Optional<Fabricator> first = Optional.empty();
    while (first.isEmpty()) {
      String name = Util.enterStringValue("fabricator's name");
      /*TODO
      first = fabricatorDAO.getAll().stream()
          .filter(s -> s.getName().compareToIgnoreCase(name) == 0).findFirst();

       */
    }
    return first.get();
  }

  //TODO
  public static Souvenir chooseSouvenir() {
    return null;
  }


}
