package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
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

public class Util {

  public static Fabricator enterFabricator() {

    return  null;
  }

  public static String enterValue(String year) {
    return null;
  }

  public static Souvenir enterSouvenir() {
    return null;
  }


  public static void restoreData() {
    try {
      restoreFabricator();
      restoreSouvenirs();
    } catch (FileNotFoundException e) {
      return;
    }
  }

  private static void restoreSouvenirs() throws FileNotFoundException {
    Gson gson = new Gson();
    List<Souvenir> coll = gson.fromJson(new FileReader(
        App.path + Souvenir.class.getCanonicalName()
    ), new TypeToken<List<Souvenir>>(){}.getType());
    DAO<Souvenir> dao = SouvenirDAO.getInstance();
    coll.forEach(dao::save);
  }

  private static void restoreFabricator() throws FileNotFoundException {
    Gson gson = new Gson();
    List<Fabricator> coll = gson.fromJson(new FileReader(
        App.path + Fabricator.class.getCanonicalName()
    ), new TypeToken<List<Fabricator>>(){}.getType());
    DAO<Fabricator> dao = FabricatorDAO.getInstance();
    coll.forEach(dao::save);
  }

  public static void saveData() {
    try {
      saveSouvenirs();
    } catch (IOException e) {
      System.out.println("Souvenris hasn't been saved");
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
