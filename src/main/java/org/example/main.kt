package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// структура фильма - есть название и длительность - общий родительский класс



abstract class MediaContent {
    abstract val title: String
    abstract val duration: Int
}



interface Rateable {
    val rating: Double
}


// тут хранятся данные - все строки про фильм. есть название, жанр, длительность и рейтинг.

data class Movie(
    override val title: String,
    val genre: String,
    override val duration: Int,
    override val rating: Double
) : MediaContent(), Rateable


object AppInfo {
    const val NAME = "Movie Match"
}


sealed class RecommendationResult {

    data class Success(
        val movie: Movie,
        val averageScore: Double
    ) : RecommendationResult()

    object NoMovies : RecommendationResult()
}



// просто функция для того чтобы распечатать название фильма и длительность
fun printContentInfo(content: MediaContent) {
    println("${content.title} | ${content.duration} min")
}


//вот тут наше условие диктуется лямбда функцией, которая возращает true/false
fun selectTopMovies(
    movies: List<Movie>,
    condition: (Movie) -> Boolean
): List<Movie> {

    return movies
        .filter(condition)
        .sortedByDescending { it.rating }
        .take(10)
}


// эта функция принимает жанр и человека
fun chooseGenre(
    genres: List<String>,
    person: String
): String {
// начинаем вопрос про выбор
    println("\nChoose genre for $person:")
// выписываем каждый жанр с порядковым номером
    for (i in genres.indices) {
        println("${i + 1}. ${genres[i]}")
    }

    var choice: Int?

    while (true) {

        print("Enter number: ")
        // вот здесь вводим выбор и переводим его в число
        choice = readln().toIntOrNull()

        if (choice != null && choice in 1..genres.size) {
            return genres[choice - 1] //возвращаем один конкретный жанр
        }
        //тут все понятно
        println("Incorrect choice. Try again.")
    }
}

//
fun readScore(person: String): Int {

    while (true) {

        print("$person score (1-10): ")

        val score = readln().toIntOrNull()

        if (score != null && score in 1..10) {
            return score
        }

        println("Score must be from 1 to 10.")
    }
}


fun findWinner(
    averageScores: List<Pair<Movie, Double>>
): RecommendationResult {

    val winner = averageScores.maxByOrNull {
        it.second
    }

    return if (winner != null) {

        RecommendationResult.Success(
            winner.first,
            winner.second
        )

    } else {

        RecommendationResult.NoMovies
    }
}


suspend fun prepareRecommendation() {

    println("\nCalculating the best movie...")
    delay(1000)

    println("Comparing your scores...")
    delay(1000)
}


fun main() = runBlocking {

    println("=== ${AppInfo.NAME} ===")


    // вот здесь создается лист - датасет с фильмами с помощью класса Movie, который мы создали.

    val movies: List<Movie> = listOf(

        Movie("The Shining", "Horror", 146, 8.4),
        Movie("Misery", "Horror", 107, 7.8),
        Movie("It", "Horror", 135, 7.3),
        Movie("The Conjuring", "Horror", 112, 7.5),
        Movie("Hereditary", "Horror", 127, 7.3),
        Movie("A Quiet Place", "Horror", 90, 7.5),
        Movie("Sinister", "Horror", 110, 6.8),
        Movie("Smile", "Horror", 115, 6.5),
        Movie("The Ring", "Horror", 115, 7.1),
        Movie("Get Out", "Horror", 104, 7.8),

        Movie("Gone Girl", "Thriller", 149, 8.1),
        Movie("Prisoners", "Thriller", 153, 8.2),
        Movie("The Menu", "Thriller", 107, 7.2),
        Movie("Se7en", "Thriller", 127, 8.6),
        Movie("Shutter Island", "Thriller", 138, 8.2),
        Movie("Nightcrawler", "Thriller", 117, 7.8),
        Movie("Zodiac", "Thriller", 157, 7.7),
        Movie("Split", "Thriller", 117, 7.3),
        Movie("Searching", "Thriller", 102, 7.6),
        Movie("The Gift", "Thriller", 108, 7.0),

        Movie("Shrek", "Comedy", 90, 7.9),
        Movie("Superbad", "Comedy", 113, 7.6),
        Movie("The Hangover", "Comedy", 100, 7.7),
        Movie("Mean Girls", "Comedy", 97, 7.1),
        Movie("Game Night", "Comedy", 100, 6.9),
        Movie("Palm Springs", "Comedy", 90, 7.4),
        Movie("The Nice Guys", "Comedy", 116, 7.4),
        Movie("Easy A", "Comedy", 92, 7.0),
        Movie("Crazy Stupid Love", "Comedy", 118, 7.4),
        Movie("21 Jump Street", "Comedy", 109, 7.2),

        Movie("Interstellar", "Drama", 169, 8.7),
        Movie("Whiplash", "Drama", 106, 8.5),
        Movie("Fight Club", "Drama", 139, 8.8),
        Movie("The Social Network", "Drama", 120, 7.8),
        Movie("Black Swan", "Drama", 108, 8.0),
        Movie("The Father", "Drama", 97, 8.2),
        Movie("Marriage Story", "Drama", 137, 7.9),
        Movie("Good Will Hunting", "Drama", 126, 8.3),
        Movie("The Truman Show", "Drama", 103, 8.2),
        Movie("Joker", "Drama", 122, 8.4)
    )



    var maxDuration: Int //заводим переменную, чтобы сохранить максимальную длительность

    while (true) {

        print("\nEnter maximum movie duration in minutes: ")

        val input = readln().toIntOrNull() //

        if (input != null && input > 0) {
            maxDuration = input
            break
        }

        println("Please enter a positive number.")
    }

// создается лист жанров
    val genres: List<String> = listOf(
        "Horror",
        "Thriller",
        "Comedy",
        "Drama"
    )

// тут вызываем функцию chooseGenre(genres List<String>, person)
    val herGenre = chooseGenre(
        genres,
        "her"
    )

    val hisGenre = chooseGenre(
        genres,
        "him"
    )

//тут сразу выбираем все фильмы которые подходят ей+длительность норм (лямбда)
    val herTopMovies = selectTopMovies(movies) { movie ->

        movie.duration <= maxDuration &&
                movie.genre == herGenre
    }

//тут сразу выбираем все фильмы которые подходят ему+длительность норм (лямбда)
    val hisTopMovies = selectTopMovies(movies) { movie ->

        movie.duration <= maxDuration &&
                movie.genre == hisGenre
    }


    println("\n=== HER TOP MOVIES ===")

    for (movie in herTopMovies) {

        printContentInfo(movie)

        println(
            "Genre: ${movie.genre} | Rating: ${movie.rating}\n"
        )
    }


    println("=== HIS TOP MOVIES ===")

    for (movie in hisTopMovies) {

        printContentInfo(movie)

        println(
            "Genre: ${movie.genre} | Rating: ${movie.rating}\n"
        )
    }


    val candidateSet: Set<Movie> =
        (herTopMovies + hisTopMovies).toSet()

    val candidateMovies: List<Movie> =
        candidateSet.toList()


    if (candidateMovies.isEmpty()) {

        println("No suitable movies found.")

        return@runBlocking
    }


    val totalDuration = candidateMovies
        .map { movie ->
            movie.duration
        }
        .reduce { total, duration ->
            total + duration
        }


    println(
        "\nTotal duration of all candidate movies: $totalDuration minutes"
    )


    val herScores: MutableMap<Movie, Int> =
        mutableMapOf()

    val hisScores: MutableMap<Movie, Int> =
        mutableMapOf()


    println("\n=== RATE THE MOVIES ===")


    for (movie in candidateMovies) {

        println("\n--------------------")
        println(movie.title)

        println(
            "${movie.genre} | ${movie.duration} min | IMDb: ${movie.rating}"
        )


        val herScore = readScore("Her")
        val hisScore = readScore("His")


        herScores[movie] = herScore
        hisScores[movie] = hisScore
    }


    val averageScores: List<Pair<Movie, Double>> =
        candidateMovies.map { movie ->

            val herScore = herScores[movie] ?: 0
            val hisScore = hisScores[movie] ?: 0

            val average =
                (herScore + hisScore) / 2.0

            movie to average
        }


    println("\n=== RESULTS ===")


    for (
    result in averageScores
        .sortedByDescending { it.second }
    ) {

        val movie = result.first
        val average = result.second

        println(
            "${movie.title} -> $average"
        )
    }



    prepareRecommendation()


    val result: RecommendationResult =
        findWinner(averageScores)


    when (result) {

        is RecommendationResult.Success -> {

            println("\n=== WINNER ===")

            println(
                "Movie: ${result.movie.title}"
            )

            println(
                "Average score: ${result.averageScore}"
            )

            println(
                "IMDb rating: ${result.movie.rating}"
            )

            println("\nEnjoy your movie!")
        }


        RecommendationResult.NoMovies -> {

            println(
                "No movie could be selected."
            )
        }
    }
}