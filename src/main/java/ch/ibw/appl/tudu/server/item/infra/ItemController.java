package ch.ibw.appl.tudu.server.item.infra;

import ch.ibw.appl.tudu.server.item.service.ItemService;
import ch.ibw.appl.tudu.server.shared.infra.JSONSerializer;
import ch.ibw.appl.tudu.server.item.model.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import org.eclipse.jetty.http.HttpStatus;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Service;

public class ItemController {
  ItemService service;

  public ItemController(boolean isTest) {
    service = new ItemService(isTest);
  }

  public void createRoutes(Service server) {
    JSONSerializer jsonSerializer = new JSONSerializer();

    server.get("/items", (request, response) -> {
      String filter = request.queryParamOrDefault("filter", "");
      if (!filter.isEmpty()) {
        return service.search(filter);
      }

      return service.all();
    }, jsonSerializer::serialize);

    server.post("/items", (request, response) -> {
      Item item = jsonSerializer.deserialize(request.body(), new TypeReference<Item>() {
      });
      Item newItem = service.create(item);

      response.status(HttpStatus.CREATED_201);
      return newItem;
    }, jsonSerializer::serialize);

    server.get("/items/:id", (request, response) -> {
      long requestedId = Long.parseLong(request.params("id"));
      return service.getById(requestedId);
    }, jsonSerializer::serialize);

    server.delete("/items/:id", (request, response) -> {
      long requestedId = Long.parseLong(request.params("id"));
      return service.deleteById(requestedId);
    }, jsonSerializer::serialize);
  }
}
