package ch.ibw.appl.tudu.server;

import ch.ibw.appl.tudu.server.shared.infra.HttpServer;

public class Main {
    public static void main(String[] args) {

        new HttpServer("4567", true).start();

    }
}
