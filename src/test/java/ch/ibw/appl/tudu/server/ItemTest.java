package ch.ibw.appl.tudu.server;

import ch.ibw.appl.tudu.server.item.model.Item;
import ch.ibw.appl.tudu.server.shared.infra.JSONSerializer;
import com.despegar.http.client.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemTest extends FunctionalTest {
    @Test
    public void get_Items_OK() {
        HttpResponse httpResponse = this.executeGet("/items");

        String body = new String(httpResponse.body());
        List<Item> deserializedItems =  new JSONSerializer().deserialize(body, new TypeReference<ArrayList<Item>>() {
        });
        Assert.assertEquals("Hello World item", deserializedItems.get(0).description);
    }

    @Test
    public void validate_response_status_code_200() {
        HttpResponse httpResponse = this.executeGet("/items");
        Assert.assertEquals(200, httpResponse.code());
    }

    @Test
    public void response_406_ifNotJSON() {
//        ACTUAL 404 .... HAS TO BE 406 -->FAILLLLLL
        HttpResponse response = this.executeGet("/items", "text/plain");
        assertEquals(HttpStatus.NOT_ACCEPTABLE_406, response.code());
    }

    @Test
    public void getItemsByIdIsOK_IfExists(){
        HttpResponse httpResponse = this.executeGet("/items/1");

        Assert.assertEquals(200, httpResponse.code());

        String body = new String(httpResponse.body());
        Item deserializedItem = new JSONSerializer().deserialize(body, new TypeReference<Item>() {});

        Assert.assertEquals( "Hallo World Item", deserializedItem.description);
    }

    @Test
    public void getItemsByIdIsNotFound_IfItemUnexisting(){
        HttpResponse httpResponse = this.executeGet("/items/22");

        Assert.assertEquals(404, httpResponse.code());
    }

    @Test
    public void createUnexistingItemIsOK(){
        HttpResponse httpResponse = this.executePost("/items", new Item( "new Item"));

        Assert.assertEquals(HttpStatus.CREATED_201, httpResponse.code());

        httpResponse = this.executeGet("/items/3");
        Assert.assertEquals(HttpStatus.OK_200, httpResponse.code());
    }

    @Test
    public void deleteExistingItemisOK(){
        HttpResponse httpResponse = this.executeDelete("/items/1");

        Assert.assertEquals(HttpStatus.OK_200, httpResponse.code());

        httpResponse = this.executeGet("/items/1");
        Assert.assertEquals(HttpStatus.NOT_FOUND_404, httpResponse.code());
    }

    @Test
    public void searchByDescriptionWithMatches(){
        HttpResponse httpResponse = this.executeGet("/items?filter=description:füR");

        Assert.assertEquals(HttpStatus.OK_200, httpResponse.code());

        String body = new String(httpResponse.body());
        List<Item> deserializedItems = new JSONSerializer().deserialize(body, new TypeReference<ArrayList<Item>>() {});

        Assert.assertEquals(1, deserializedItems.size());
    }

    @Test
    public void create_todo_validationFailed() {
        Object item = new Item("");
        HttpResponse response = executePost("/items", item);

        assertEquals(HttpStatus.BAD_REQUEST_400, response.code());
        assertEquals("application/json", response.headers().get("Content-Type").get(0));

        String body = new String(response.body());
        assertTrue(body.contains("message"));
    }
}

