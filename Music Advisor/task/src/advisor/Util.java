package advisor;

import com.wrapper.spotify.SpotifyApi;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

import static advisor.Actions.takePlaylists;
import static advisor.ServerUtil.createAndStartServer;

public class Util {
    public static String AUTH_SERVER = "https://accounts.spotify.com";
    public static String API_SERVER = "https://api.spotify.com"; //resource server
    public static final String REDIRECT_URI = "http://localhost:8080";
    public static final String CLIENT_ID = "a7254441bc474bd6adf67eed4d885e44";
    public static final String SECRET_ID = "b2373bfe88cd449db9d34421118dc875";
    public static final String AUTHORIZE_PART = "/authorize";
    public static final String RESPONSE_TYPE = "code";
    public static final String TOKEN_PART = "/api/token";
    public static final String GRANT_TYPE = "authorization_code&";
    public static String DENIED_AUTH = "Please, provide access for application.";
    public static boolean isAuth = false;
    public static String code = "";
    static String accessToken = "";
    static String refreshToken = "";
    SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(CLIENT_ID)
            .setClientSecret(SECRET_ID)
            .setRedirectUri(URI.create(REDIRECT_URI))
            .build();

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        Util.accessToken = accessToken;
    }

    public static void setIsAuth(boolean isAuth) {
        Util.isAuth = isAuth;
    }
    //spotifyApi.getAccessToken();
    public static void setApiServer(String apiServer) {
        API_SERVER = apiServer;
    }

    public static void setAuthServer(String authServer) {
        AUTH_SERVER = authServer;
    }

    public static void auth() throws IOException, InterruptedException {
//        System.out.println("https://accounts.spotify.com/authorize?" +
//               "client_id=a7254441bc474bd6adf67eed4d885e44&" +
//               "redirect_uri=http://localhost:8080&response_type=code");
//        //System.out.println("---SUCCESS---");
        createAndStartServer();
        isAuth = true;
    }

    public static void parseInput() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            String input = scanner.nextLine();
            switch (input) {
                case "auth":
                    auth();
                    break;
                case "new":
//                    if (isAuth) {
//                        Actions.getNewReleases();
//                    } else {
//                        System.out.println(DENIED_AUTH);
//                    }
                    //Actions.takeNewReleases();
                    //Actions.TestJSON();
//                    if(isAuth) {
//                        TestNewReleases.getListOfNewReleases_Sync();
//                    }else{
//                        System.out.println(DENIED_AUTH);
//                    }
                    UsingTheWrapper.getListOfNewReleases_Sync();
                    //Actions.spotifyRequestNewNOTWORKINGEITHER();

                    break;

                case "featured":
//                    if (isAuth) {
//                        Actions.getFeatured();
//                    } else {
//                        System.out.println(DENIED_AUTH);
//                    }
                    //Actions.takeFeatured();
                    UsingTheWrapper.getListOfFeaturedPlaylists_Sync();

                    break;

                case "categories":
//                    if (isAuth) {
//                        Actions.getCategories();
//                    } else {
//                        System.out.println(DENIED_AUTH);
//                    }
                    //Actions.takeCategories();
                    UsingTheWrapper.getListOfCategories_Sync();
                    break;

                //case "playlist":
//                    String playlistType = scanner.nextLine();
//
//                    String type = "";
//                    if (playlistType.split(" ").length > 1) {
//                        type = (playlistType.split(" "))[1];
//                    }
//                    if (isAuth) {
//                        Actions.getPlaylists(type);
//                    } else {
//                        System.out.println(DENIED_AUTH);
//                    }

                case "exit":
                    System.exit(0);
                    break;

                default:
                    if (input.startsWith("playlists")) {
                        //takePlaylists(input);
                        UsingTheWrapper.getListOfCurrentUsersPlaylists_Sync();
                    }
                    break;
            }

        }
    }

//    public static void newReleases() {
////        System.out.println("---NEW RELEASES---\n" +
////                "Mountains [Sia, Diplo, Labrinth]\n" +
////                "Runaway [Lil Peep]\n" +
////                "The Greatest Show [Panic! At The Disco]\n" +
////                "All Out Life [Slipknot]");
//
//
//    }
//
//    public static void featured() {
//        System.out.println("---FEATURED---\n" +
//                "Mellow Morning\n" +
//                "Wake Up and Smell the Coffee\n" +
//                "Monday Motivation\n" +
//                "Songs to Sing in the Shower");
//    }
//
//    public static void categories() {
//        System.out.println("---CATEGORIES---\n" +
//                "Top Lists\n" +
//                "Pop\n" +
//                "Mood\n" +
//                "Latin");
//    }
//
//    public static void playlists(String name) {
//        System.out.println("---" + name.toUpperCase() + " PLAYLISTS---\n" +
//                "Walk Like A Badass\n" +
//                "Rage Beats\n" +
//                "Arab Mood Booster\n" +
//                "Sunday Stroll");
//    }
//
//    public static void playlists() {
//        System.out.println("--- MOOD PLAYLISTS---\n" +
//                "Walk Like A Badass\n" +
//                "Rage Beats\n" +
//                "Arab Mood Booster\n" +
//                "Sunday Stroll");
//    }

}
