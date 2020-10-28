package advisor;

import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static advisor.Util.getAccessToken;

public class Actions {
    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final Map<String, String> categoriesId = new LinkedHashMap<>();
    private static List<String> output = new ArrayList<>();
    private static final List<String> name = new ArrayList<>();
    private static final List<String> artist = new ArrayList<>();
    private static final List<String> link = new ArrayList<>();


    public static void accessToken(String accessServer) throws IOException, InterruptedException {
        System.out.println("making http request for access_token...");
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(accessServer + Util.TOKEN_PART))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "client_id=" + Util.CLIENT_ID +
                                "&client_secret=" + Util.SECRET_ID +
                                "&grant_type=" + Util.GRANT_TYPE +
                                Util.RESPONSE_TYPE +
                                "&redirect_uri=" + Util.REDIRECT_URI))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Util.setAccessToken(JsonParser.parseString(response.body()).getAsJsonObject().get("access_token").getAsString());
        Util.setIsAuth(true);
        System.out.println("Success!");
    }

    public static void spotifyRequestNewNOTWORKINGEITHER() throws IOException, InterruptedException {

        // Parsing received JSON

        List<JsonObject> objects = new ArrayList<>();
        //String json = spotifyRequestAuth(apiPath);
        JsonObject jo = JsonParser.parseString(sendGetRequest("new-releases")).getAsJsonObject();
        JsonObject albums = jo.get("albums").getAsJsonObject();
        JsonArray newReleasesArray = albums.getAsJsonArray("items");

        for (int i = 0; i < newReleasesArray.size(); i++) {
            objects.add(newReleasesArray.get(i).getAsJsonObject());
        }

        // Printing data to user

        for (JsonObject object : objects) {

            // Getting URL data

            JsonObject externalUrl = object.get("external_urls").getAsJsonObject();

            // Getting artists data

            JsonArray artists = object.getAsJsonArray("artists");
            StringBuilder artistsString = new StringBuilder();
            List<JsonObject> artistsNames = new ArrayList<>();
            for (int i = 0; i < artists.size(); i++) {
                artistsNames.add(artists.get(i).getAsJsonObject());
            }
            artistsString.append("[");
            for (JsonObject artist : artistsNames) {
                artistsString.append(artist.get("name")).append(", ");
            }
            artistsString.replace(artistsString.length() - 2, artistsString.length(), "");
            artistsString.append("]");

            name.add(object.get("name").getAsString());
            artist.add(artistsString.toString().replace("\"", ""));
            link.add(externalUrl.get("spotify").toString().replace("\"", ""));

        }
    }


        public static List<String> getNewReleases() throws IOException, InterruptedException {
        JsonObject jo = JsonParser.parseString(sendGetRequest("new-releases")).getAsJsonObject();
        JsonObject albums = jo.getAsJsonObject("albums");
        JsonArray albumArray = albums.getAsJsonArray("items");

        List<String> musiciansNames = new ArrayList<>();
        output.clear();
        for (JsonElement album : albumArray.getAsJsonArray()) {
            for (JsonElement artist : album.getAsJsonObject().getAsJsonArray("artists")) {
                musiciansNames.add(artist.getAsJsonObject().get("name").getAsString());
            }
            output.add(album.getAsJsonObject().get("name").getAsString() + "\n" + musiciansNames.toString() + "\n" +
                    album.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString() + "\n");
            musiciansNames.clear();
        }
        return output;
    }

    public static List<String> getFeatured() throws IOException, InterruptedException {
        JsonObject jo = JsonParser.parseString(sendGetRequest("featured-playlists")).getAsJsonObject();
        JsonObject playlists = jo.getAsJsonObject("playlists");
        output.clear();
        for (JsonElement playlist : playlists.getAsJsonArray("items")) {
            output.add(playlist.getAsJsonObject().get("name").getAsString() + "\n" +
                    playlist.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString() + "\n");
        }
        return output;
    }

    public static void TestJSON(){
        String json = "{\"name\":\"John\",\"address\":{\"city\":\"London\",\"plot\":12},\"age\":23,\"hobbies\":[\"music\",\"tech news\",\"blog\"]}";
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        String name = jo.get("name").getAsString();
        int age = jo.get("age").getAsInt();

        JsonObject addressObj = jo.getAsJsonObject("address");
        String city = addressObj.get("city").getAsString();
        int plot = addressObj.get("plot").getAsInt();

        List<String> hobbies = new ArrayList<>();
        for (JsonElement hobby : jo.getAsJsonArray("hobbies")) {
            hobbies.add(hobby.getAsString());
        }
        //System.out.println(age);

        UsingTheWrapper.getListOfNewReleases_Sync();
    }

    public static List<String> getCategories() throws IOException, InterruptedException {
        saveCategories();
        return new ArrayList<>(categoriesId.keySet());
    }

    static void saveCategories() throws IOException, InterruptedException {
        categoriesId.clear();
        JsonObject jo = JsonParser.parseString(sendGetRequest("categories")).getAsJsonObject();
        JsonObject categories = jo.getAsJsonObject("categories");
        for (JsonElement category : categories.getAsJsonArray("items")) {
            String categoryName = category.getAsJsonObject().get("name").getAsString();
            categoriesId.put(categoryName, category.getAsJsonObject().get("id").getAsString());
        }
    }

    static List<String> getPlaylists(String categoryName) throws IOException, InterruptedException {
        saveCategories();
        String categoryId;
        output.clear();
        if (categoriesId.containsKey(categoryName)) {
            categoryId = categoriesId.get(categoryName);
        } else {
            System.out.println("Unknown category name.");
            return output;
        }
        JsonObject jo = JsonParser
                .parseString(sendGetRequest("categories/" + categoryId + "/playlists"))
                .getAsJsonObject();
        JsonObject playlists = jo.getAsJsonObject("playlists");
        if (playlists != null) {
            for (JsonElement x : playlists.getAsJsonArray("items")) {
                output.add(x.getAsJsonObject().get("name").getAsString() + "\n" +
                        x.getAsJsonObject().get("external_urls").getAsJsonObject().get("spotify").getAsString() + "\n");
            }
        } else {
            System.out.println(jo.getAsJsonObject("error").get("message").getAsString());
        }
        return output;
    }
    static void takeNewReleases() throws IOException, InterruptedException {
        if (Util.isAuth) {
            output = getNewReleases();
            //pageTurner.setTurningMethods(new TurningPagesNew(elementsNumber, output));
            //pageTurner.turnPageForward();
        } else {
            System.out.println(Util.DENIED_AUTH);
        }
    }

    static void takeFeatured() throws IOException, InterruptedException {
        if (Util.isAuth) {
            output = getFeatured();
            //pageTurner.setTurningMethods(new TurningPagesFeatured(elementsNumber, output));
            //pageTurner.turnPageForward();
        } else {
            System.out.println(Util.DENIED_AUTH);
        }
    }

    static void takeCategories() throws IOException, InterruptedException {
        if (Util.isAuth) {
            output = getCategories();
            //pageTurner.setTurningMethods(new TurningPagesCategories(elementsNumber, output));
            //pageTurner.turnPageForward();
        } else {
            System.out.println(Util.DENIED_AUTH);
        }
    }

    static void takePlaylists(String option) throws IOException, InterruptedException {
        if (Util.isAuth) {
            String[] command = option.split("\\s+");
            if (command.length > 1) {
                output = getPlaylists(option.substring(command[0].length() + 1));
                //pageTurner.setTurningMethods(new TurningPagesPlaylists(elementsNumber, output));
                //pageTurner.turnPageForward();
            }
        } else {
            System.out.println(Util.DENIED_AUTH);
        }
    }

    static String sendGetRequest(String endpoint) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + getAccessToken())
                .uri(URI.create(Util.API_SERVER + "/v1/browse/" + endpoint))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
    }

//    public static String getAccessToken() {
//        return accessToken;
//    }
//
//    public static void setAccessToken(String accessToken) {
//        Actions.accessToken = accessToken;
//    }

}
