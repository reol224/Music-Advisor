package advisor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerUtil {
    public static void createAndStartServer() throws IOException, InterruptedException {
        //This class implements a simple HTTP server.
        //creates a HttpServer instance which is initially not bound to any local address/port.
        // The HttpServer is acquired from the currently installed HttpServerProvider
        // The server MUST be bound using bind(InetSocketAddress,int) before it can be used.
        HttpServer httpServer = HttpServer.create();

        //Binds a currently unbound HttpServer to the given address and port number.
        //InetSocketAddress(int port) creates a socket address where the IP address is the wildcard address and the port number a specified value.
        //A valid port value is between 0 and 65535.
        //A port number of zero will let the system pick up an ephemeral port in a bind operation
        httpServer.bind(new InetSocketAddress(8080), 0);


        System.out.println("use this link to request the access code:");
        System.out.println(Util.AUTH_SERVER + Util.AUTHORIZE_PART
                + "?client_id=" + Util.CLIENT_ID
                + "&redirect_uri=" + Util.REDIRECT_URI
                + "&response_type=" + Util.RESPONSE_TYPE);
        System.out.println("waiting for code...");
        httpServer.start();

        //public abstract HttpContext createContext(String path, HttpHandler handler)
        //Creates a HttpContext.
        //A HttpContext represents a mapping from a URI path to a exchange handler on this HttpServer.
        //Once created, all requests received by the server for the path will be handled by calling the given handler object.
        httpServer.createContext("/",
                exchange -> {
                    String query = exchange.getRequestURI().getQuery();
                    String result = null;
                    if (query != null && query.contains("code")) {
                        Util.code = query.substring(5);
                        result = "Got the code. Return back to your program.";
                    } else {
                        result = "Authorization code not found. Try again.";
                    }
                    exchange.sendResponseHeaders(200, result.length());
                    exchange.getResponseBody().write(result.getBytes());
                    exchange.getResponseBody().close();
                    //System.out.println(result);
                }
        );

        while (Util.code.equals("")) {
            Thread.sleep(2);
        }
        httpServer.stop(1);

        System.out.println("Making http request for access_token...");

        HttpRequest requestForAccessToken = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(
                        "client_id=" + Util.CLIENT_ID
                                + "&client_secret=" + Util.SECRET_ID
                                + "&grant_type=" + Util.GRANT_TYPE
                                + "&code=" + Util.code
                                + "&redirect_uri=" + Util.REDIRECT_URI))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(Util.AUTH_SERVER + Util.TOKEN_PART))
                .build();

        HttpResponse<String> responseWithAccessToken = HttpClient
                .newBuilder()
                .build()
                .send(requestForAccessToken,
                        HttpResponse.BodyHandlers.ofString());

        String fullToken = responseWithAccessToken.body();
        if(fullToken.contains("access_token")){
            Util.accessToken = fullToken.substring(17,179); //always 162 chars
        }
//        if(fullToken.contains("refresh_token")){
//            Util.refreshToken = fullToken.substring(238,369); //always 131 chars
//        }
        System.out.println("Access code is : " + Util.accessToken);
        //System.out.println("Refresh token is : " + Util.refreshToken);
        System.out.println("response:\n" + fullToken + "\n" + "Success!");
        //System.out.println("Success!");

    }
}
