package repositories

import data.Synopsis

object SynopsisRepositories {
    val synopsis = mutableListOf<Synopsis>()


    init {
        synopsis.add(
            Synopsis(
                1,
                "Narra el encuentro entre un aviador y un niño que viaja por el universo. El principito le cuenta al aviador sus aventuras en diferentes planetas y le enseña el valor del amor y la amistad. El cuento es una reflexión sobre la vida, la inocencia, la imaginación y la crítica a la sociedad adulta"
            )
        )

        synopsis.add(
            Synopsis(
                2,
                "Desde que 100 años atrás los titanes apareciesen de la nada y llevasen a la humanidad al borde de la extinción, la población vive encerrada en ciudades rodeadas de enormes muros llamados Maria, Rose y Sina, con el fin de protegerse de la aparición de nuevos titanes. La historia de un joven que sueña con el poder estar fuera de los muros."
            )
        )

        synopsis.add(
            Synopsis(
                3,
                "Abbey Road es el undécimo álbum de estudio (duodécimo en estados unidos) publicado por la banda británica de rock The Beatles, El álbum se caracterizó por la presencia de un medley en el lado B, una larga pieza de 16 minutos, que constaba de ocho canciones enlazadas una tras otra sucesivamente. También fue producido y orquestado por George Martin, con Geoff Emerick como ingeniero de grabación"
            )
        )

        synopsis.add(
            Synopsis(
                4,
                "El doctor Ariel ha fundado una institución altruista cuya misión consiste en proporcionar consuelo, ilusión y alegría a las personas desesperadas, carentes de dicha y de fe1. La obra juega con la fantasía y la realidad en el teatro, y se presenta como una escenificación del teatro dentro del teatro"
            )
        )

        synopsis.add(
            Synopsis(
                5,
                "Concebido como un álbum conceptual, explora temas como el conflicto, la codicia, el tiempo, la muerte y las enfermedades mentales. Fue grabado en los estudios Abbey Road en Londres y se basa en ideas previas de la banda. La portada, diseñada por Storm Thorgerson, representa un espectro prismático."
            )
        )

        synopsis.add(
            Synopsis(
                6,
                "este álbum se caracteriza por su heterogeneidad de contenidos, que abarcan géneros como el rap, el rock, el trap y el funk. A lo largo de sus canciones, Wos aborda temas como la protesta social, las fiestas con amigos, el amor y el desamor1. El disco incluye sencillos como “Canguro” y “Melón vino”."
            )
        )

        synopsis.add(
            Synopsis(
                7,
                "Este álbum es una continuación del extended play (EP) Animal, parte 1, que se lanzó en febrero del mismo año. Incluye las cuatro canciones del EP y otras siete nuevas. El álbum cuenta con colaboraciones de artistas como Cazzu, Tiago PZK, Becky G y Danny Ocean.Algunos de los sencillos destacados del álbum son “Cazame” y “Mi debilidad”. La portada muestra las manos de Becerra agarrando su logo en forma de corazón, simbolizando su entrega transparente y fuerte al público"
            )
        )

        synopsis.add(
            Synopsis(
                8,
                "Cien años de soledad es una novela que cuenta la historia de la familia Buendía y su maldición, que castiga el matrimonio entre parientes dándoles hijos con cola de cerdo. La historia transcurre entre la boda de José Arcadio Buendía con Amelia Iguarán hasta la maldición de Aureliano Babilonia, abarcando todo un siglo. La novela gira en torno a la familia Buendía, una estirpe que fundó el pueblo de Macondo. A lo largo de varias generaciones, los Buendía experimentan tragedias, amores prohibidos, guerras y misterios que se entrelazan con el destino de la familia y del propio pueblo."
            )
        )

        synopsis.add(
            Synopsis(
                9,
                " La historia comienza cuando Harry Potter, un niño huérfano, descubre que es hijo de dos destacados hechiceros y ha heredado poderes mágicos. A los once años, ingresa a la escuela Hogwarts de Magia y Hechicería, donde aprenderá todo lo necesario para convertirse en mago. En este mágico mundo, Harry se enfrentará a criaturas, objetos mágicos y desafíos mientras descubre su verdadera identidad y destino."
            )
        )


        synopsis.add(
            Synopsis(
                10,
                "1984 es una novela de George Orwell que narra la historia de Winston Smith, un hombre que vive en una sociedad totalitaria donde el Gran Hermano lo vigila todo. Winston trabaja en el Ministerio de la Verdad, donde altera la historia según los intereses del partido. Winston se enamora de Julia, una mujer que también se rebela contra el régimen, y ambos son perseguidos por la Policía del Pensamiento. 1984 es una crítica a los totalitarismos y a la opresión del poder"
            )
        )


    }

    fun getById(id : Long): Synopsis {
        return synopsis.find { it.id == id }!!
    }
}