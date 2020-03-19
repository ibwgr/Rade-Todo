import spark.Service;

import java.util.ArrayList;
import java.util.List;

public class ItemController {
    List<Item> items;
    public ItemController() {
        items = new ArrayList<>();
        Item item1 = new Item();
        item1.description = "Hello World item";
        items.add(item1);

    }

    public void createRoutes(Service server) {
        //spark startet per default auf port 4567
        server.get("/items", (request, response) -> {

//            response.type("application/json");

            if (request.contentType() == "application/json") {
                response.status(200);
            }else{
                response.status(406);
            }

            return items;
        }, items -> new JSONSerializer().serialize(items)); //response transformer von spark, interface zum implementieren


    }
}
