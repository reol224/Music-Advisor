package advisor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Util {
    private static String AUTH_SERVER = "https://accounts.spotify.com";
    public static  String API_SERVER = "https://api.spotify.com";
    private static String REDIRECT_URI = "http://localhost:8080";
    private static String CLIENT_ID = "a7254441bc474bd6adf67eed4d885e44";
    private static String SECRET_ID = "b2373bfe88cd449db9d34421118dc875";
    public static final String AUTHORIZE_PART = "/authorize";
    public static final String RESPONSE_TYPE = "code";
    public static final String TOKEN_PART = "/api/token";
    public static final String GRANT_TYPE = "authorization_code";
    private static String DENIED_AUTH = "Please, provide access for application.";
    public static boolean isAuth = false;
    public static String code = "";

    public static void setAuthServer(String authServer){
        AUTH_SERVER = authServer;
    }

    public static void makeServer() throws IOException, InterruptedException {
        //This class implements a simple HTTP server.
        //creates a HttpServer instance which is initially not bound to any local address/port.
        // The HttpServer is acquired from the currently installed HttpServerProvider
        // The server MUST be bound using bind(InetSocketAddress,int) before it can be used.
        HttpServer httpServer = HttpServer.create();

        //Binds a currently unbound HttpServer to the given address and port number.
        //InetSocketAddress(int port) creates a socket address where the IP address is the wildcard address and the port number a specified value.
        //A valid port value is between 0 and 65535.
        //A port number of zero will let the system pick up an ephemeral port in a bind operation
        httpServer.bind(new InetSocketAddress(8080),0);


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
                    String result;
                    if (query != null && query.contains("code")) {
                        code = query.substring(5);
                        result = "code received";
                    } else {
                        result = "Not found authorization code. Try again.";
                    }
                    exchange.sendResponseHeaders(200, result.length());
                    exchange.getResponseBody().write(result.getBytes());
                    exchange.getResponseBody().close();
                    System.out.println(result);
                }
            );
                    while (code.equals("")) {
                        Thread.sleep(2);
                    }
                    httpServer.stop(1);

                    System.out.println("Making http request for access_token...");

                    HttpRequest requestForAccessToken = HttpRequest.newBuilder()
                            .POST(HttpRequest.BodyPublishers.ofString(
                                    "client_id=" + Util.CLIENT_ID
                                            + "&client_secret=" + Util.SECRET_ID
                                            + "&grant_type=" + Util.GRANT_TYPE
                                            + "&code=" + code
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
                    System.out.println("response:\n" + fullToken + "\n" + "---SUCCESS---");

                }


    public static void auth() throws IOException, InterruptedException {
//        System.out.println("https://accounts.spotify.com/authorize?" +
//               "client_id=a7254441bc474bd6adf67eed4d885e44&" +
//               "redirect_uri=http://localhost:8080&response_type=code");
//        //System.out.println("---SUCCESS---");
        makeServer();
        isAuth = true;
    }
    public static void parseInput() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
//            String playlistType = scanner.nextLine();
//
//            String type = "";
//            if (playlistType.split(" ").length > 1) {
//                type = (playlistType.split(" "))[1];
//            }
            String input = scanner.nextLine();
            switch (input) {
                case "auth":
                    auth();
                    break;
                case "new":
                    if (isAuth) {
                        newReleases();
                    } else {
                        System.out.println(DENIED_AUTH);
                    }

                    break;

                case "featured":
                    if (isAuth) {
                        featured();
                    } else {
                        System.out.println(DENIED_AUTH);
                    }
                    break;

                case "categories":
                    if (isAuth) {
                        categories();
                    } else {
                        System.out.println(DENIED_AUTH);
                    }
                    break;

                case "playlist Mood":
                    if (isAuth) {
                        playlists();
                    } else {
                        System.out.println(DENIED_AUTH);
                    }
                    break;

                case "exit":
                    exit();
                    break;
            }

        }
    }
    public static void newReleases(){
        System.out.println("---NEW RELEASES---\n" +
                "Mountains [Sia, Diplo, Labrinth]\n" +
                "Runaway [Lil Peep]\n" +
                "The Greatest Show [Panic! At The Disco]\n" +
                "All Out Life [Slipknot]");
    }

    public static void featured(){
        System.out.println("---FEATURED---\n" +
                "Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower");
    }

    public static void categories(){
        System.out.println("---CATEGORIES---\n" +
                "Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin");
    }

    public static void playlists(String name){
        System.out.println("---" + name.toUpperCase() + " PLAYLISTS---\n" +
                "Walk Like A Badass\n" +
                "Rage Beats\n" +
                "Arab Mood Booster\n" +
                "Sunday Stroll");
    }

    public static void playlists(){
        System.out.println("--- MOOD PLAYLISTS---\n" +
                "Walk Like A Badass\n" +
                "Rage Beats\n" +
                "Arab Mood Booster\n" +
                "Sunday Stroll");
    }

    public static void exit(){
        System.out.println("---GOODBYE!---");
        System.exit(0);
    }
}
