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

        new ItemController().createRoutes(server);

        server.awaitInitialization();
    }

    public void stop() {
        server.stop();
        server.awaitStop();
    }
}