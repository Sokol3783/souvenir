package org.example.utils;

import static org.example.App.IN;

import java.util.Optional;
import org.example.dao.DAO;
import org.example.models.Fabricator;
import org.example.models.Souvenir;

public interface Chooser<T> {

  T choose();

  public static class FabricatorChooser<T extends Fabricator> implements Chooser {

    private final DAO<Fabricator> dao;

    public FabricatorChooser(DAO<Fabricator> dao) {
      this.dao = dao;
    }

    @Override
    public Fabricator choose() {
      printInfo();
      while (IN.hasNextLine()) {
        if (IN.hasNextInt()) {
          switch (IN.nextInt()) {
            case 1 -> { return findFabricatorByName(); }
            case 2 -> { return findFabricatorById(); }
            default -> System.out.println("Invalid input, enter '1' or '2'");
          }
        }
      }
      return null;
    }

    private void printInfo() {
      System.out.println("""
          1. Find by name.
          2. Find by id.
          Enter value number: 
          """);
    }

    private Fabricator findFabricatorById() {
      Optional<Fabricator> first = Optional.empty();
      while (first.isEmpty()) {
        first = dao.get(Util.enterIntValue("id"));
      }
      return first.get();
    }

    private Fabricator findFabricatorByName() {
      Optional<Fabricator> first = Optional.empty();
      while (first.isEmpty()) {
        String name = Util.enterStringValue("fabricator's name");
        first = dao.getAll().stream()
            .filter(s -> s.getName().compareToIgnoreCase(name) == 0).findFirst();
      }
      return first.get();
    }
  }

  public static class SouvenirChooser<T extends Souvenir> implements Chooser {

    public SouvenirChooser(DAO<Souvenir> souvenirDAO) {
    }

    @Override
    public T choose() {
      return null;
    }
  }

}
