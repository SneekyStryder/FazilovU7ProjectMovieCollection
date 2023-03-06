import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie object to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        ArrayList<String> castList = new ArrayList<>();

        for (Movie movie : movies) {
            String[] castDump;
            castDump = movie.getCast().split("\\|");
            for (String member : castDump) {
                for (int i = 0; i < castList.size(); i++) {
                    if (member.equals(castList.get(i))) {
                        castList.remove(i);
                    }
                }
            }

            for (String member : castDump) {
                castList.add(member);
            }
        }

        System.out.print("Enter a cast member name: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();

        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < castList.size(); i++) {
            if (castList.get(i).toLowerCase().indexOf(searchTerm) >= 0) {
                results.add(castList.get(i));
            }
        }

        for (int i = 0; i < results.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < results.size(); j++) {
                if (results.get(j).compareTo(results.get(maxIndex)) > 0) {
                    maxIndex = j;
                }
            }
            if (i != maxIndex) {
                String temp = results.set(i, results.get(maxIndex));
                results.set(maxIndex, temp);
            }
        }
        ArrayList<String> resultsSorted = new ArrayList<>();
        for (int i = results.size() - 1; i >= 0; i--) {
            resultsSorted.add(results.get(i));
        }

        for (int i = 0; i < resultsSorted.size(); i++) {
            String member = resultsSorted.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + member);
        }

        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectMember = resultsSorted.get(choice - 1);

        ArrayList<Movie> movieResults = new ArrayList<>();
        for (Movie movie : movies) {
            String[] castDump;
            castDump = movie.getCast().split("\\|");
            for (int i = 0; i < castDump.length; i++) {
                if (selectMember.equals(castDump[i])) {
                    movieResults.add(movie);
                }
            }
        }

        sortResults(movieResults);
        for (int i = 0; i < movieResults.size(); i++) {
            String movie = movieResults.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + movie);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = movieResults.get(choice2 - 1);
        displayMovieInfo((selectedMovie));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++) {
            String movieKeyword = movies.get(i).getKeywords();
            movieKeyword = movieKeyword.toLowerCase();

            if (movieKeyword.indexOf(searchTerm) != -1) {
                results.add(movies.get(i));
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++) {
            String movie = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + movie);
        }

        System.out.println("Which keyword would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> genreList = new ArrayList<>();

        for (Movie movie : movies) {
            String[] genreDump = movie.getGenres().split("\\|");
            for (String genre : genreDump) {
                for (int i = 0; i < genreList.size(); i++) {
                    if (genre.equals(genreList.get(i))) {
                        genreList.remove(i);
                    }
                }
            }

            for (String genre : genreDump) {
                genreList.add(genre);
            }
        }

        for (int i = 0; i < genreList.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < genreList.size(); j++) {
                if (genreList.get(j).compareTo(genreList.get(maxIndex)) > 0) {
                    maxIndex = j;
                }
            }
            if (i != maxIndex) {
                String temp = genreList.set(i, genreList.get(maxIndex));
                genreList.set(maxIndex, temp);
            }
        }
        ArrayList<String> genreListSorted = new ArrayList<>();
        for (int i = genreList.size() - 1; i >= 0; i--) {
            genreListSorted.add(genreList.get(i));
        }


        for (int i = 0; i < genreListSorted.size(); i++) {
            String genre = genreListSorted.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + genre);
        }

        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectGenre = genreListSorted.get(choice - 1);

        ArrayList<Movie> movieResults = new ArrayList<>();
        for (Movie movie : movies) {
            String[] genreDump;
            genreDump = movie.getGenres().split("\\|");
            for (int i = 0; i < genreDump.length; i++) {
                if (selectGenre.equals(genreDump[i])) {
                    movieResults.add(movie);
                }
            }
        }

        sortResults(movieResults);
        for (int i = 0; i < movieResults.size(); i++) {
            String movie = movieResults.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + movie);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice2 = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = movieResults.get(choice2 - 1);
        displayMovieInfo((selectedMovie));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> top50 = new ArrayList<>();
        ArrayList<Movie> moviesCopy = new ArrayList<>();
        moviesCopy = movies;

        int highestRated = 0;
        for (int i = 0; i < moviesCopy.size(); i++) {
            if (moviesCopy.get(i).getUserRating() > moviesCopy.get(highestRated).getUserRating()) {
                highestRated = i;
            }
            if (top50.size() >= 50) {
                break;
            }
            else {
                top50.add(movies.get(highestRated));
            }
            highestRated = i + 1;
        }

        for (int i = 0; i < top50.size(); i++) {
            Movie movie = top50.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + movie.getTitle() + ": " + movie.getUserRating());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = top50.get(choice - 1);
        displayMovieInfo((selectedMovie));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> top50 = new ArrayList<>();
        ArrayList<Movie> moviesCopy = new ArrayList<>();
        moviesCopy = movies;

        int highestRev = 0;
        for (int i = 0; i < moviesCopy.size(); i++) {
            if (moviesCopy.get(i).getRevenue() > moviesCopy.get(highestRev).getRevenue()) {
                highestRev = i;
            }
            if (top50.size() >= 50) {
                break;
            }
            else {
                top50.add(movies.get(highestRev));
            }
            highestRev = i + 1;
        }

        for (int i = 0; i < top50.size(); i++) {
            Movie movie = top50.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + movie.getTitle() + ": " + movie.getRevenue());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = top50.get(choice - 1);
        displayMovieInfo((selectedMovie));
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}