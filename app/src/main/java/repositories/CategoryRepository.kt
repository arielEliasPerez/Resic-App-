package repositories

import data.Category

object CategoryRepository {
    private val bookCategories = mutableListOf<Category>()
    private val musicCategories = mutableListOf<Category>()

    init {
        bookCategories.add(Category(1L, "Novela distópica"))
        bookCategories.add(Category(2L, "Fantasy"))
        bookCategories.add(Category(3L, "Realismo mágico"))
        bookCategories.add(Category(4L, "Obra teatral"))
        bookCategories.add(Category(5L, "Manga"))
        bookCategories.add(Category(6L, "Literatura infantil"))

        musicCategories.add(Category(1L, "Rock"))
        musicCategories.add(Category(2L, "Progressive Rock"))
        musicCategories.add(Category(3L, "Hip Hop"))
        musicCategories.add(Category(4L, "Reggaeton"))
    }

    fun getBookCategories(): List<Category> {
        return this.bookCategories
    }

    fun getMusicCategories(): List<Category> {
        return this.musicCategories
    }
}