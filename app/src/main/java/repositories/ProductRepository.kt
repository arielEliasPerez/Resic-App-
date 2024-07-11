package repositories

import com.example.resicapp.R
import data.Product
import data.ProductClasification
import data.ProductType

object ProductRepository {

    private val products = mutableListOf<Product>()

    init {
        products.add(
            Product(
                1L,
                "El Principito",
                ProductType.BOOK,
                ProductClasification.SILVER,
                "1943/04/06",
                "Literatura infantil",
                4.7F,
                150000.00,
                 (R.drawable.el_principito),
                "Antoine Saint-Exupery",
                synopsis = SynopsisRepositories.getById(1).data

            )
        )

        products.add(
            Product(
                2L,
                "Shingeki no Kyojin",
                ProductType.BOOK,
                ProductClasification.PLATINUM,
                "2009/09/09",
                "Manga",
                4.9F,
                1500.00,
                (R.drawable.shingeki_no_kyojin),
                "Hajime Isayama",
                synopsis = SynopsisRepositories.getById(2).data
            )
        )

        products.add(
            Product(
                3L,
                "Abbey Road",
                ProductType.DISC,
                ProductClasification.GOLD,
                "1969/09/24",
                "Rock",
                5.0F,
                55000.00,
                 (R.drawable.abbey_road),
                "The Beatles",
                synopsis = SynopsisRepositories.getById(3).data
            )
        )

        products.add(
            Product(
                4L,
                "Los arboles mueren de pie",
                ProductType.BOOK,
                ProductClasification.BRONZE,
                "1949/04/01",
                "Obra teatral",
                2.3F,
                35000.00,
                (R.drawable.los_arboles_mueren_de_pie),
                "Alejandro Casona",
                synopsis = SynopsisRepositories.getById(4).data
            )
        )

        products.add(
            Product(
                5L,
                "Dark Side of the Moon",
                ProductType.DISC,
                ProductClasification.PLATINUM,
                "1973/03/01",
                "Progressive Rock",
                5.0F,
                60000.00,
                (R.drawable.dark_side_of_the_moon),
                "Pink Floyd",
                synopsis = SynopsisRepositories.getById(5).data
            )
        )

        products.add(
            Product(
                6L,
                "Caravana",
                ProductType.DISC,
                ProductClasification.GOLD,
                "2020/10/23",
                "Hip Hop",
                4.6F,
                52000.00,
                (R.drawable.wos_caravana),
                "Wos",
                synopsis = SynopsisRepositories.getById(6).data
            )
        )

        products.add(
            Product(
                7L,
                "Animal",
                ProductType.DISC,
                ProductClasification.SILVER,
                "2021/07/16",
                "Reggaeton",
                4.4F,
                49000.00,
                (R.drawable.maria_becerra_animal),
                "María Becerra",
                synopsis = SynopsisRepositories.getById(7).data
            )
        )

        products.add(
            Product(
                8L,
                "Cien años de soledad",
                ProductType.BOOK,
                ProductClasification.GOLD,
                "1967/05/30",
                "Realismo mágico",
                4.8F,
                450.00,
                (R.drawable.cien_a_soledad),
                "Gabriel García Márquez",
                synopsis = SynopsisRepositories.getById(8).data
            )
        )

        products.add(
            Product(
                9L,
                "Harry Potter y la piedra filosofal",
                ProductType.BOOK,
                ProductClasification.BRONZE,
                "1997/06/26",
                "Fantasy",
                4.9F,
                650.00,
                (R.drawable.harry_potter_piedra_filosofal),
                "J.K. Rowling",
                synopsis = SynopsisRepositories.getById(9).data
            )
        )

        products.add(
            Product(
                10L,
                "1984",
                ProductType.BOOK,
                ProductClasification.GOLD,
                "1949/06/08",
                "Novela distópica",
                4.6F,
                480.00,
                (R.drawable.mil_nueve_ocho_cuatro),
                "George Orwell",
                synopsis = SynopsisRepositories.getById(10).data
            )
        )

    }

    fun get(): List<Product> {
        return this.products
    }

    fun getById(id: Long): Product {
       return products.find { it.id == id }!!
    }


}