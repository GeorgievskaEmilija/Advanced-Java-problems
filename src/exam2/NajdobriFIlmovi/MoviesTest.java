package kolokviumski2code.NajdobriFIlmovi;


import java.util.*;
import java.util.stream.Collectors;

class Movie implements Comparable<Movie> {
    String title;
    int[] ratings;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = ratings;
    }

    public float getAvgRating() {
        double avg = Arrays.stream(ratings).asDoubleStream().average().getAsDouble();
        return (float) avg;
    }

    public float ratingCoef(int maxratings) {
        return (getAvgRating() * ratings.length) / maxratings;
        //просечен ретјтинг на филмот x вкупно број на рејтинзи на филмот / максимален број на рејтинзи (од сите филмови во листата)

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int[] getRatings() {
        return ratings;
    }

    public int getRatingsSize() {
        return ratings.length;
    }

    public void setRatings(int[] ratings) {
        this.ratings = ratings;
    }


    @Override
    public String toString() {
        //  Marriage Italian Style (1964) (6.38) of 26 ratings
        return String.format("%s (%.2f) of %d ratings", title, getAvgRating(), ratings.length);
    }

    @Override
    public int compareTo(Movie o) {
        if (ratingCoef(MoviesList.maxRatings) == o.ratingCoef(MoviesList.maxRatings))
            return title.compareTo(o.title);
        return -Float.compare(ratingCoef(MoviesList.maxRatings), o.ratingCoef(MoviesList.maxRatings));
    }
}

class MoviesList {
    List<Movie> movies;
    static int maxRatings;

    public MoviesList() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(String title, int[] ratings) {
        movies.add(new Movie(title, ratings));
    }

    public List<Movie> top10ByAvgRating() {
        return movies.stream().sorted(Comparator.comparing(Movie::getAvgRating).reversed()
                .thenComparing(Movie::getTitle)).limit(10).collect(Collectors.toList());
    }

    public int getMaxRatingssize() {
        List<Integer> nizaNaDolzini = new ArrayList<>();
        movies.stream().forEach(m -> nizaNaDolzini.add(m.ratings.length));
        return nizaNaDolzini.stream().mapToInt(Integer::intValue).max().getAsInt();
    }

    public List<Movie> top10ByRatingCoef() {
        maxRatings = movies.stream().mapToInt(Movie::getRatingsSize).max().getAsInt();
        movies.sort(Movie::compareTo);
        return movies.stream().limit(10).collect(Collectors.toList());

    }
}

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}
