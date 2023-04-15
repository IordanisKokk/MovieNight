# Movie Night Application

## Aim

The aim of this Android application is to make finding movies to watch easier and less stressful. The user look up movies by their titles and filter the results by genre. This feature can easily be extended to incorporate other filters like ratings, release dates, etc. The user can also add movies to their "Favourites" list, as well as to dismiss movies he has already seen, so that they don't show up when he is searching for new movies to watch. 

**<img title="" src="https://lh3.googleusercontent.com/AW1epkHOMRfnF70WKU7piG-RtVIqJw-DMNZsZTbxTEj_26fD8jj1LQ35lL0mpBVta7p2iLOvJcCS7wZGeBqRiTVLmHb2grCOgtMbhZHp-uuBR47iYMby8CBFjdyS4_oPerpxI1fLACwH7LPEagYx0w" alt="" data-align="inline" width="296">** **<img title="" src="https://lh3.googleusercontent.com/uFUvZeQOXa7l5PBeLHKbsUGvAX3O_ZgECMU5yhSC0JCkOH8pj_ZqWdrDBI0CQrNaPrYehxJaLyk0_gN-mt5OoapYikmoTDudU5Asj_RqHPKSp_VPnlhc_iolKolzp0GAPqE5OW8zPBSgaoCbm33mYQ" alt="" width="296">**

## Database

The application uses a SQLite database to store information about movies, as well as tables for managing user's favorite and ignored movies. Access to the database is done through the DbHandler class, which provides methods for querying the database.

The SearchUtil and DisFavUtil classes make use of DbHandler's methods to execute database queries based on user input. Specifically, the SearchUtil class includes methods for finding movies by their title or title and genre, returning an ArrayList of MovieModel objects. The MovieModel class is used to represent a movie stored in the database and includes fields for the movie's ID, title, release date, rating, description, poster file path, and categories.

The DisFavUtil class, on the other hand, includes methods for managing a user's favorite and ignored movies, returning an ArrayList of MovieModel objects that correspond to the user's preferences. These methods make use of the user's ID and movie ID, which are managed through DbHandler's corresponding methods.

## Activities and Fragments

The application is composed of three activities: MainActivity, LoginRegistrationActivity, and SettingsActivity. In MainActivity, there's a FragmentContainerView which contains two fragments: Home (renamed to Favourites in the UI) and Search. Additionally, there's a Settings option that leads to the third activity, SettingsActivity. To move between Home (Favourites) and Search, the user can use the BottomNavigationMenu. If they access Settings, they can go back using a button provided in that activity. Similarly, in LoginRegistrationActivity, there's a FragmentContainerView which includes loginFragment and registerFragment. The user can move between these fragments using the BottomNavigationMenu.

### Home Fragment

The homeFragment contains a ConstraintLayout that includes a RecyclerView. The movie cards are added to the RecyclerView using the findFavourites function from the homeFragment.java file.

### Search Fragment

The searchFragment contains an EditText for the user to search for a movie based on its title. Additionally, there is a ChipGroup that contains 9 chips, which the user can select to filter their search for specific movie categories. Finally, there is the SearchButton, which the user presses when they want to perform a search. The button sends the user's input (i.e., the movie title and selected categories) to a new searchResultsFragment via a Bundle, and then changes the displayed fragment to the searchResultsFragment.

**<img title="" src="https://lh5.googleusercontent.com/yXFKcLR9gwcMNBRBbDRoHZrznRVY4T8Au-J0uunjPngqt2bRlIlYNE7vm7-uftFVkn01Bp07OXiu1zWqr9vXwvuLR14BK8KA8gwqV-Zdp6ymz03G060d4pP63MJrbMSH0EqPXJtxkZjsT4GYFEjahw" alt="" width="255" data-align="center">**

## Login Fragment

The loginFragment is one of the two fragments in the LoginRegistrationActivity, and essentially contains a form for user input. It includes EditText fields for the user's email and password, as well as a button to initiate the login process. The input provided by the user in the EditText fields is used to authenticate the user through Firebase.

## Registration Fragment

The registerFragment is a component of LoginRegistrationActivity and serves as a form for user input. It comprises EditText fields for email, username, password, and password confirmation. Upon completion, the user can initiate the registration process by clicking on the registration button, which uses the user's input to register through Firebase.

## SearchResults Fragment

The SearchResults fragment is similar to the HomeFragment, but it also includes a TextView at the top that shows the keyword the user searched for to find movies. The search results are displayed below the TextView.

## MovieCard Fragment

MovieCard is a fragment that presents each movie as a card within a RecyclerView. It includes an ImageView for the movie poster, TextViews for the title, release date, rating, directors, and summary of the movie.

In addition, it has three buttons: "More Info", which directs the user to the movie's page on IMDb when clicked, "Favourite/Unfavourite", which adds or removes the movie from the user's favourites list, and "Dismiss", which hides the movie for the user until they either delete the app or click "Reset Dismissed Movies" in the settings.

## Settings Activity

This activity (i.e., its .xml file) has options for changing the user's email and password, as well as buttons for resetting favorite movies, resetting dismissed movies, going back to the home screen, and logging out. This fragment is activated by the SettingsActivity class and the necessary listeners are added to handle the buttons that the user may press.

## MovieListAdapter##

This is a RecyclerView adapter class used to pass data from an ArrayList of MovieModels to the corresponding RecyclerView. It also has an internal MovieViewHolder class, which accepts a View and sets the attributes of the MovieModel on it.

This is done with bindMovie, which after giving the View the content it should display, dynamically adds any genres the corresponding movie should display, and also adds corresponding OnClickListeners to the buttons of the fragmentMovieCard that handle button clicks.

## WatchTogether class

This is a helper class that allows for the setting and getting of the user id so that it is always available.


