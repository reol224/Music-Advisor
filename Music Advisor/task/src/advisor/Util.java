package advisor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Util {
    private static String REDIRECT_URI = "http://localhost:8080";
    private static String CLIENT_ID = "a7254441bc474bd6adf67eed4d885e44";
    private static String DENIED_AUTH = "Please, provide access for application.";
    static boolean calledAuth = false;

    public static void connection() throws IOException, InterruptedException {
        //creating the HttpClient instance
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(REDIRECT_URI))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest,HttpResponse
                .BodyHandlers
                .ofString());
    }

    public static void auth(){
        calledAuth = true;
        String link = String.format("https://accounts.spotify.com/authorize?" +
                "client_id=%s" +
                "&redirect_uri=%s" +
                "&response_type=code",
                REDIRECT_URI,
                CLIENT_ID);
        System.out.println(link);
        System.out.println("---SUCCESS---");
    }
    public static void parseInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            switch (input) {
                case "auth":
                    auth();
                    break;
                case "new":
                    if (calledAuth) {
                        newReleases();
                    } else {
                        System.out.println(DENIED_AUTH);
                    }

                    break;

                case "featured":
                    if (calledAuth) {
                        featured();
                    } else {
                        System.out.println(DENIED_AUTH);
                    }
                    break;

                case "categories":
                    if (calledAuth) {
                        categories();
                    } else {
                        System.out.println(DENIED_AUTH);
                    }
                    break;

                case "playlists Mood":
                    if (calledAuth) {
                        moodPlaylists();
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

    public static void moodPlaylists(){
        System.out.println("---MOOD PLAYLISTS---\n" +
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
