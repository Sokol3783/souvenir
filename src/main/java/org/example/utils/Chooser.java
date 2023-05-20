package org.example.utils;

import org.example.models.Fabricator;
import org.example.models.Souvenir;

public interface Chooser<T> {
    T choose();

    public static class FabricatorChooser<T extends Fabricator> implements Chooser<T> {

      @Override
      public T choose() {
        return null;
      }
    }

  public static class SouvenirChooser<T extends Souvenir> implements Chooser<T> {

    @Override
    public T choose() {
      return null;
    }
  }

}
