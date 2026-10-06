# Movie Match

Movie Match is a Kotlin console application that helps two people choose a movie to watch together.

The program first asks for the maximum movie duration. Then each person selects a preferred genre. The application selects up to 10 highest-rated suitable movies for each person and combines the lists without duplicates.

Both users rate each candidate movie from 1 to 10. The program calculates the average score for every movie and selects the movie with the highest average score.

## How to Run

1. Clone or download the repository.
2. Open the project in IntelliJ IDEA.
3. Make sure Gradle dependencies are loaded.
4. Run the `main()` function in `Main.kt`.
5. Follow the instructions in the console.

## Kotlin Concepts Used

The project demonstrates the main Kotlin concepts required in the assignment:

- Variables and data types
- Conditions and loops
- `List`, `Set`, and `Map`
- Collection operations: `map`, `filter`, and `reduce`
- Functions
- Higher-order functions and lambdas
- Classes and objects
- Inheritance
- Interfaces and polymorphism
- Data classes
- Sealed classes
- A suspend function
- A simple coroutine using `runBlocking` and `delay`

### Examples

- `Movie` is a data class.
- `MediaContent` is an abstract parent class inherited by `Movie`.
- `Rateable` is an interface implemented by `Movie`.
- `selectTopMovies()` is a higher-order function that receives a lambda condition.
- `RecommendationResult` is a sealed class.
- `prepareRecommendation()` is a suspend function.
- `runBlocking` is used to run the coroutine in the console application.