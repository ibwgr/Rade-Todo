import com.despegar.http.client.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

}
