package ch.ibw.appl.tudu.server;

import ch.ibw.appl.tudu.server.item.model.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import org.eclipse.jetty.http.HttpStatus;
import spark.Service;

import java.util.ArrayList;
import java.util.List;

public class ItemController {
    List<Item> items;
    public ItemController() {
        items = new ArrayList<>();
        Item item1 = new Item(1L, "Hallo World Item");
        items.add(item1);
    }


    public void createRoutes(Service server) {
        //spark startet per default auf port 4567
        JSONSerializer jsonSerializer = new JSONSerializer();

        server.get("/items",
                (request, response) -> items,
                jsonSerializer::serialize);

        server.post("/items",
                (request, response) -> {
                    Item item = jsonSerializer.deserialize(request.body(), new TypeReference<Item>() {
                    });

                    items.add(item);

                    response.status(HttpStatus.CREATED_201);
                    return "";
                },
                jsonSerializer::serialize);

        server.get("/items/:id", (request, response) -> {
            long requestedId = Long.parseLong(request.params("id"));

            for (Item item : items) {
                if (item.id == requestedId) {
                    return item;
                }
            }

            return null;
        }, jsonSerializer::serialize);

        server.delete("/items/:id", (request, response) -> {
            long requestedId = Long.parseLong(request.params("id"));

            for (Item item : items) {
                if (item.id == requestedId) {
                    items.remove(item);
                    return "";
                }
            }

            return null;
        }, jsonSerializer::serialize);

    }

}
