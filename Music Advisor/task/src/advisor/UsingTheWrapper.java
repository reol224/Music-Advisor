package advisor;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.FeaturedPlaylists;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.Category;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.requests.data.browse.GetListOfCategoriesRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfFeaturedPlaylistsRequest;
import com.wrapper.spotify.requests.data.browse.GetListOfNewReleasesRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.Arrays;

public class UsingTheWrapper {

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(Util.accessToken)
            .build();

    //GET LIST OF NEW RELEASES
    private static final GetListOfNewReleasesRequest getListOfNewReleasesRequest = spotifyApi.getListOfNewReleases()
//          .country(CountryCode.SE)
//          .limit(10)
//          .offset(0)
            .build();

    public static void getListOfNewReleases_Sync() {
        try {
            final Paging<AlbumSimplified> albumSimplifiedPaging = getListOfNewReleasesRequest.execute();

            System.out.println("Total: \n" + Arrays.toString(albumSimplifiedPaging.getItems()) + "\n");
            System.out.println();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            if(e.getMessage().contains("expired")){
                //method to refresh the access token
                CC.clientCredentials_Sync();
            }
        }
    }
    //GET FEATURED PLAYLISTS
    private static final GetListOfFeaturedPlaylistsRequest getListOfFeaturedPlaylistsRequest = spotifyApi
            .getListOfFeaturedPlaylists()
            .country(CountryCode.RO)
//          .limit(10)
//          .offset(0)
//          .timestamp(new Date(1414054800000L))
            .build();

    public static void getListOfFeaturedPlaylists_Sync() {
        try {
            final FeaturedPlaylists featuredPlaylists = getListOfFeaturedPlaylistsRequest.execute();

            System.out.println(Arrays.toString(featuredPlaylists.getPlaylists().getItems()));
            //System.out.println("Message: " + featuredPlaylists.getMessage());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            if(e.getMessage().contains("expired")){
                //method to refresh the access token
                CC.clientCredentials_Sync();
            }
        }
    }

    //GET LIST OF CATEGORIES
    private static final GetListOfCategoriesRequest getListOfCategoriesRequest = spotifyApi.getListOfCategories()
//          .country(CountryCode.SE)
//          .limit(10)
//          .offset(0)
//          .locale("sv_SE")
            .build();

    public static void getListOfCategories_Sync() {
        try {
            final Paging<Category> categoryPaging = getListOfCategoriesRequest.execute();

            System.out.println(Arrays.toString(categoryPaging.getItems()));
            //System.out.println("Total: " + categoryPaging.getTotal());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            if(e.getMessage().contains("expired")){
                //method to refresh the access token
                CC.clientCredentials_Sync();
            }
        }
    }

    //GET A LIST OF CURRENT USERS PLAYLIST
    private static final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
            .getListOfCurrentUsersPlaylists()
//          .limit(10)
//          .offset(0)
            .build();

    public static void getListOfCurrentUsersPlaylists_Sync() {
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();

            System.out.println(Arrays.toString(playlistSimplifiedPaging.getItems()));
            //System.out.println("Total: " + playlistSimplifiedPaging.getTotal());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
