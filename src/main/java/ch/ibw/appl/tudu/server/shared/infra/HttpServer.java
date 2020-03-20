package ch.ibw.appl.tudu.server.shared.infra;

import ch.ibw.appl.tudu.server.ItemController;
import spark.Service;

public class HttpServer {

    private final String httpPort;
    private Service server;

    public HttpServer(String httpPort) {
        this.httpPort = httpPort;
    }

    public void start() {
        server = Service.ignite();
        server.port(Integer.parseInt(httpPort));

        server.before((request, response) -> {
            if (!request.headers("accept").equalsIgnoreCase("application/json")) {
                server.halt(406);
            }
        });

        server.afterAfter((request, response) -> {
            response.type("application/json");
        });

        new ItemController().createRoutes(server);

        server.awaitInitialization();
    }

    public void stop() {
        server.stop();
        server.awaitStop();
    }
}