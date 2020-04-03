package ch.ibw.appl.tudu.server.item.service;

import ch.ibw.appl.tudu.server.item.model.Item;
import ch.ibw.appl.tudu.server.shared.model.ValidationError;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
  private final List<Item> items;
  private long nextId = 0;

  public ItemService(boolean isTest) {
    items = new ArrayList<>();
    if(isTest){
      Item item1 = new Item("Hallo World Item");
      Item item2 = new Item("Einkaufen für Geburtstag");
      this.create(item1);
      this.create(item2);
    }
  }

  public Item create(Item item) {
    if(item.description == null ||item.description.isEmpty()){
      throw new ValidationError("description can not be empty");
    }
    item.id = ++nextId;
    items.add(item);
    return item;
  }

  public Item getById(long requestedId) {
    for (Item item : this.all()) {
      if (item.id == requestedId) {
        return item;
      }
    }
    return null;
  }

  public List<Item> all() {
    return items;
  }

  public Item deleteById(long requestedId) {
    for (Item item : this.all()) {
      if (item.id == requestedId) {
        items.remove(item);
        return item;
      }
    }
    return null;
  }

  public List<Item> search(String filter) {
    String[] keyValue = filter.split(":");
    List<Item> matches = new ArrayList<>();

    if (keyValue[0].equalsIgnoreCase("description")) {
      String searchTerm = keyValue[1].toLowerCase();

      for (Item item : items) {
        if (item.description.toLowerCase().contains(searchTerm)) {
          matches.add(item);
        }
      }

    }
    return matches;
  }
}
