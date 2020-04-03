package ch.ibw.appl.tudu.server.item.model;

import java.util.Date;

public class Item {
  public String description;
  public Date createdAt;
  public Long id;

  // wird für jackson gebraucht
  public Item() { }

  public Item(long id, String description) {
    this.id = id;
    this.description = description;
  }

  public Item(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Item{" +
            "description='" + description + '\'' +
            ", createdAt=" + createdAt +
            ", id=" + id +
            '}';
  }
}
