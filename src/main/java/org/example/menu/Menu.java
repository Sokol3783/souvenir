package org.example.menu;

import static org.example.App.IN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.Data;


public class Menu {
    //Comparator.comparing(Item::getNumber)
  SortedMenuList<Item> items;

  public void runMenu() {
    printMenu();
    while (IN.hasNext()){
      if (IN.hasNextInt()) {
        int command = IN.nextInt();
        runCommand(command);
      } else if (IN.hasNextLine()) {
        String line = IN.nextLine();
        if (line.compareToIgnoreCase("exit") == 0) {
          return;
        }
      }
      printMenu();
    }
  }

  private void runCommand(int command) {
    Optional<Item> first = items.findByNumber(command);
    if (first.isPresent()){
      first.get().item.run();
    } else {
      System.out.println("Unknown menu item, enter valid number");
    }
  }

  private void printMenu() {
    items.printMenu();
  }

  private static class SortedMenuList<T extends Item> {

    private final Comparator<T> comparator;
    private List<T> list;

    public SortedMenuList(Comparator<T> comparator) {
      this.comparator = comparator;
      this.list = new ArrayList<>();
    }

    public int size() {
      return list.size();
    }

    public boolean add(T o) {
      list.add(o);
      sortAndDistinct();
      return true;
    }

    public Optional<T> get(int index) {
      return list.stream().filter(s -> s.getNumber() == index).findFirst();
    }

    public boolean addAll(Collection<? extends T> c) {
      list.addAll(c);
      sortAndDistinct();
      return true;
    }

    private void sortAndDistinct() {
      int[] n = {1};
      list = list.stream().distinct().sorted(comparator).map(s -> {
        int i = n[0];
        s.setNumber(i++);
        n[0] = n[0];
        return s;
      }).toList();

    }

    public void remove(T o){
      list.removeIf(s -> s.equals(o));
    }

    public void printMenu() {
      list.stream().forEach(s-> System.out.println(s));
    }

    public Optional<T> findByNumber(int command) {
      return list.stream().filter(s -> s.getNumber() == command).findFirst();
    }
  }

  @Data
  public static class Item {

    private int number;
    private String nameItem;
    final private Runnable item;

    public int getNumber() {
      return number;
    }

    public void setNumber(int number) {
      this.number = number;
    }

    public String toString() {
      return String.format("%3d %30s", number, nameItem);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Item item)) {
        return false;
      }

      return getNameItem().equals(item.getNameItem());
    }

    @Override
    public int hashCode() {
      return getNameItem().hashCode();
    }
  }

}
