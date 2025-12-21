import retrofit2.Call
import retrofit2.http.GET

interface WallpaperApi {
    @GET("SEU_REPO/main/wallpapers.json")
    fun getWallpapers(): Call<WallpaperResponse>
}
