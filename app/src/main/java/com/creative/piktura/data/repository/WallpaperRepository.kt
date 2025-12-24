object WallpaperRepository {

    private const val DATA_URL =
        "https://piktura.pages.dev/wallpapers.json"

    fun fetch(callback: (List<Wallpaper>) -> Unit) {
        Thread {
            try {
                val json = java.net.URL(DATA_URL).readText()
                val obj = org.json.JSONObject(json)
                val array = obj.getJSONArray("wallpapers")

                val list = mutableListOf<Wallpaper>()

                for (i in 0 until array.length()) {
                    val item = array.getJSONObject(i)
                    list.add(
                        Wallpaper(
                            id = item.getInt("id"),
                            title = item.getString("title"),
                            image_url = item.getString("image_url")
                        )
                    )
                }

                android.os.Handler(android.os.Looper.getMainLooper()).post {
                    callback(list)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}
