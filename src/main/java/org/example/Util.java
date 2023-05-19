package org.example;

import static org.example.App.IN;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import lombok.SneakyThrows;
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
    while (IN.hasNext()){

      if (IN.hasNextLine()){
        String value = IN.nextLine();
        if (!value.isEmpty()){
          return value;
        }
      }

      printMessageAndLine(nameValue, "Invalid input, please repeat");
    }
    return "";
  }

  public static int enterIntValue(String nameValue) {
    printLine(nameValue);
    while (IN.hasNext()){
      if (IN.hasNextInt()){
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

  @SneakyThrows
public static void restoreData() {
    restoreFabricator();
    restoreSouvenirs();
  }

  private static void restoreSouvenirs() throws FileNotFoundException {
    Gson gson = new Gson();
    List<Souvenir> coll = gson.fromJson(new FileReader(
        App.path + Souvenir.class.getCanonicalName()
    ), new TypeToken<List<Souvenir>>(){}.getType());
    DAO<Souvenir> dao = SouvenirDAO.getInstance();
    coll.forEach(dao::update);
  }

  private static void restoreFabricator() throws FileNotFoundException {
    Gson gson = new Gson();
    List<Fabricator> coll = gson.fromJson(new FileReader(
        App.path + Fabricator.class.getCanonicalName()
    ), new TypeToken<List<Fabricator>>(){}.getType());
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
    try(FileWriter writer = new FileWriter(App.path + Souvenir.class.getCanonicalName())){
      writer.write(serialized);
    }
  }

  private static void saveFabricator() throws IOException {
    Gson gson = new Gson();
    List all = FabricatorDAO.getInstance().getAll();
    String serialized = gson.toJson(all);
    try(FileWriter writer = new FileWriter(App.path + Fabricator.class.getCanonicalName())){
      writer.write(serialized);
    }
  }
}
