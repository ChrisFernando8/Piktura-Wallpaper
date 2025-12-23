class WallpaperAdapter(
    private val list: List<Wallpaper>,
    private val onClick: (Wallpaper) -> Unit
) : RecyclerView.Adapter<WallpaperAdapter.ViewHolder>() {

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgWallpaper)
    }

    override fun onCreateViewHolder(p: ViewGroup, v: Int): ViewHolder {
        val view = LayoutInflater.from(p.context)
            .inflate(R.layout.item_wallpaper, p, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(h: ViewHolder, pos: Int) {
        val wallpaper = list[pos]

        Glide.with(h.img)
            .load(wallpaper.imageUrl)
            .centerCrop()
            .into(h.img)

        h.itemView.setOnClickListener {
            onClick(wallpaper)
        }
    }

    override fun getItemCount() = list.size
}
