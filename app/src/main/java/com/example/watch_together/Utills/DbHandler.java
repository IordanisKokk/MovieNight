package com.example.watch_together.Utills;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.watch_together.models.MovieModel;

import java.util.ArrayList;

/**
 *
 * This is a class that handles the creation and queries to the database.
 * The database is created in the onCreate method.
 * A method called onCreateInsertValues is called by the onCreate method to insert entries on our db
 * tables.
 *
 * Finally, there are  TWO methods that handle queries to the database, one for queries only using a movie_title,
 * and one with movie_genres.
 *
 */
public class DbHandler extends SQLiteOpenHelper {

    private ArrayList<MovieModel> movies = new ArrayList<>();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movieNightDB.db";

    public static final String TABLE_MOVIES = "movies";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_MOVIE_TITLE = "movie_title";
    public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";
    public static final String COLUMN_MOVIE_DIRECTORS = "directors";
    public static final String COLUMN_MOVIE_RATING = "movie_rating";
    public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";
    public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";

    public static final String TABLE_MOVIE_GENRES = "movie_genres";
    public static final String COLUMN_GENRE_MOVIE_ID = "movie_id";
    public static final String COLUMN_GENRE = "genre";

    public static final String TABLE_FAVOURITES = "favourites";
    public static final String COLUMN_FAVOURITE_ID = "favourite_id";
    public static final String COLUMN_USER_ID = "user_id";

    public static final String TABLE_DISMISSED = "dismissed";
    public static final String COLUMN_DISMISSED_ID = "dismissed_id";


    public DbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MOVIES_TABLE = new StringBuilder().append("CREATE TABLE ").append(TABLE_MOVIES).append("(").append(COLUMN_MOVIE_ID).append(" VARCHAR(20) NOT NULL,").append(COLUMN_MOVIE_TITLE).append(" VARCHAR NOT NULL,").append(COLUMN_MOVIE_RELEASE_DATE).append(" DATE NOT NULL,").append(COLUMN_MOVIE_DIRECTORS).append(" VARCHAR NOT NULL,").append(COLUMN_MOVIE_RATING).append(" DOUBLE NOT NULL,").append(COLUMN_MOVIE_OVERVIEW).append(" VARCHAR NOT NULL,").append(COLUMN_MOVIE_POSTER_PATH).append(" VARCHAR NOT NULL,").append("PRIMARY KEY (").append(COLUMN_MOVIE_ID).append(")").append(");").toString();

//        String CREATE_MOVIE_GENRES_TABLE = new StringBuilder().append("CREATE TABLE ").append(TABLE_MOVIE_GENRES).append("(").append(COLUMN_MOVIE_ID).append(" INT NOT NULL,").append(COLUMN_GENRE).append(" VARCHAR NOT NULL,").append("PRIMARY KEY (").append(COLUMN_MOVIE_ID).append(", ").append(COLUMN_GENRE).append(")").append("FOREIGN KEY (").append(COLUMN_MOVIE_ID).append(" REFERENCES ").append(TABLE_MOVIES).append("(").append(COLUMN_MOVIE_ID).append(")").append(")").toString();
        String CREATE_MOVIE_GENRES_TABLE = "CREATE TABLE movie_genres (movie_id INTEGER NOT NULL, genre VARCHAR NOT NULL,PRIMARY KEY (movie_id, genre), FOREIGN KEY (movie_id) REFERENCES movie(movie_id));";
        String CREATE_FAVOURITES_TABLE = new StringBuilder().append("CREATE TABLE ").append(TABLE_FAVOURITES).append("(").append(COLUMN_FAVOURITE_ID).append(" INT AUTO_INCREMENT, ").append(COLUMN_USER_ID).append(" VARCHAR(20) NOT NULL, ").append(COLUMN_MOVIE_ID).append(" VARCHAR NOT NULL, ").append("FOREIGN KEY(").append(COLUMN_MOVIE_ID).append(") REFERENCES ").append(TABLE_MOVIES).append(" (").append(COLUMN_MOVIE_ID).append(") ON DELETE CASCADE, ").append("PRIMARY KEY (").append(COLUMN_FAVOURITE_ID).append(")").append(");").toString();
        String CREATE_DISMISSED_TABLE = new StringBuilder().append("CREATE TABLE ").append(TABLE_DISMISSED).append("(").append(COLUMN_DISMISSED_ID).append(" INT AUTO_INCREMENT, ").append(COLUMN_USER_ID).append(" VARCHAR(20) NOT NULL, ").append(COLUMN_MOVIE_ID).append(" VARCHAR NOT NULL, ").append("FOREIGN KEY(").append(COLUMN_MOVIE_ID).append(") REFERENCES ").append(TABLE_MOVIES).append(" (").append(COLUMN_MOVIE_ID).append(") ON DELETE CASCADE, ").append("PRIMARY KEY (").append(COLUMN_DISMISSED_ID).append(")").append(");").toString();

        sqLiteDatabase.execSQL(CREATE_MOVIES_TABLE);
        sqLiteDatabase.execSQL(CREATE_MOVIE_GENRES_TABLE);
        sqLiteDatabase.execSQL(CREATE_FAVOURITES_TABLE);
        sqLiteDatabase.execSQL(CREATE_DISMISSED_TABLE);

        onCreateInsertValues(sqLiteDatabase);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE_GENRES);
        onCreate(sqLiteDatabase);

    }

    public void onCreateInsertValues(SQLiteDatabase sqLiteDatabase) {
//        String INSERT_INTO_MOVIES = new StringBuilder().append("INSERT INTO ").append(TABLE_MOVIES).append("(").append(COLUMN_MOVIE_TITLE).append(", ").append(COLUMN_MOVIE_RELEASE_DATE).append(", ").append(COLUMN_MOVIE_RATING).append(", ").append(COLUMN_MOVIE_OVERVIEW).append(", ").append(COLUMN_MOVIE_TITLE).append(", ").append(COLUMN_MOVIE_POSTER_PATH).append(") VALUES ").append("      ('The Shawshank Redemption', '1994/09/10', 9.2, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', '/res/drawable/images/shawshank.png'),\n").append("      ('The Godfather', '1972/03/14', 9.2, 'The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.', '/res/drawable/images/godfather.png'),\n").append("      ('The Dark Knight', '2008/07/14', 9.0, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', '/res/drawable/images/darkknight.png'),\n").append("      ('The Godfather: Part II', '1972/03/14', 9.0, 'The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.', '/res/drawable/images/godfather2.png'),\n").append("      ('12 Angry Men', '1957/4/10', 9.0, 'The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.', '/res/drawable/images/twelveangrymen.png'),\n").append("      (\"Schindler's List\", '1993/00/00', 9.0, 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', '/res/drawable/images/schindlerslist.png');").toString();

        String INSERT_INTO_MOVIE_GENRES = new StringBuilder().append("INSERT INTO ").append(TABLE_MOVIE_GENRES).append(" VALUES ").append("(1,'Drama'),\n").append("(2, 'Drama'),\n").append("('tt0468569', 'Action'),\n").append("(3, 'Drama'),\n").append("(4, 'Drama'),\n").append("(5, 'Drama'),\n").append("(6, 'Drama');").toString();

        String INSERT_INTO_MOVIES = "INSERT INTO movies (" + COLUMN_MOVIE_ID + ", " + COLUMN_MOVIE_TITLE + ", " + COLUMN_MOVIE_RELEASE_DATE + ", " + COLUMN_MOVIE_DIRECTORS + ", " + COLUMN_MOVIE_RATING + ", " + COLUMN_MOVIE_OVERVIEW + ", " + COLUMN_MOVIE_POSTER_PATH + ")\n" +
                "    VALUES\n" +
                "      ('1', 'The Shawshank Redemption', '1994/09/10', 'Frank Darabont', 9.2, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'doctor_strange'),\n" +
                "      ('2', 'The Godfather', '1972/03/14', 'Francis Ford Coppola', 9.2, 'The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.', 'doctor_strange'),\n" +
                "      ('tt0468569', 'The Dark Knight', '2008/07/14', 'Christopher Nolan', 9.0, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'poster_tt0468569'),\n" +
                "      ('4', 'The Godfather: Part II', '1972/03/14', 'Francis Ford Coppola', 9.0, 'The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.', 'doctor_strange'),\n" +
                "      ('5', '12 Angry Men', '1957/4/10', 'Reginald Rose', 9.0, 'The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.', 'doctor_strange'),\n" +
                "      ('6', \"Schindler's List\", '1993/00/00', 'Steven Spielberg', 9.0, 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 'doctor_strange');\n";

        sqLiteDatabase.execSQL(INSERT_INTO_MOVIES);
        sqLiteDatabase.execSQL(INSERT_INTO_MOVIE_GENRES);

    }

    public ArrayList<MovieModel> findFavouritesByUserID(String userID) {
        String query = "SELECT " + TABLE_MOVIES + "." + COLUMN_MOVIE_ID + ", " + TABLE_MOVIES + "." + COLUMN_MOVIE_TITLE + ", " + TABLE_MOVIES + "." + COLUMN_MOVIE_RELEASE_DATE + ", " + TABLE_MOVIES + "." + COLUMN_MOVIE_DIRECTORS + ", " + TABLE_MOVIES + "." + COLUMN_MOVIE_RATING + ", " +TABLE_MOVIES + "." + COLUMN_MOVIE_OVERVIEW + ", " +TABLE_MOVIES + "." + COLUMN_MOVIE_POSTER_PATH + " FROM " + TABLE_FAVOURITES + ", " + TABLE_MOVIES + " WHERE " +
                TABLE_FAVOURITES + "." + COLUMN_USER_ID + "='" + userID + "' AND " + TABLE_MOVIES + "." + COLUMN_MOVIE_ID + "=" + TABLE_FAVOURITES + "." + COLUMN_MOVIE_ID + " AND " + TABLE_MOVIES + "." + COLUMN_MOVIE_ID + " NOT IN (SELECT " +
                COLUMN_MOVIE_ID + " FROM " + TABLE_DISMISSED + " WHERE " + COLUMN_USER_ID + "='" + userID + "');";
        Log.d("de", query);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        movies = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {
            MovieModel movie = new MovieModel();
            movie.setMovieID(cursor.getString(0));
            movie.setTitle(cursor.getString(1));
            movie.setReleaseDate(cursor.getString(2));
            movie.setDirectors(cursor.getString(3));
            movie.setVoteAverage(Float.parseFloat(cursor.getString(4)));
            movie.setMovieOverview(cursor.getString(5));
            movie.setPosterPath(cursor.getString(6));
            movies.add(movie);
        }

        sqLiteDatabase.close();
        return movies;
    }

    public void favouriteMovieByID(String userID, String movieID) {
        String query = "INSERT INTO " + TABLE_FAVOURITES + "(" + COLUMN_USER_ID + ", " + COLUMN_MOVIE_ID + ")" + " VALUES ('" + userID + "', '" + movieID + "');";
        Log.d("de", query);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }

    public void unfavouriteMovieByID(String userID, String movieID) {
        String query = "DELETE FROM " + TABLE_FAVOURITES + " WHERE " + COLUMN_USER_ID + "='" + userID + "' AND " + COLUMN_MOVIE_ID + "='" + movieID + "';";
        Log.d("de", query);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }

    public boolean isFavouriteMovieByID(String userID, String movieID) {
        String query = "SELECT * FROM " + TABLE_FAVOURITES + " WHERE " + COLUMN_USER_ID + "='" + userID + "' AND " + COLUMN_MOVIE_ID + "='" + movieID + "';";
        Log.d("de", query);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        Log.d("de", "Found " + cursor.getCount() + "favourite movie(s).");
        boolean isFavourite = cursor.getCount() > 0;
        sqLiteDatabase.close();
        return isFavourite;
    }

    public void resetFavouritesByUserID(String userID) {
        String query = "DELETE FROM " + TABLE_FAVOURITES + " WHERE " + COLUMN_USER_ID + "='" + userID + "';";
        Log.d("de", query);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }

    public void dismissMovieByID(String userID, String movieID) {
        String query = "INSERT INTO " + TABLE_DISMISSED + "(" + COLUMN_USER_ID + ", " + COLUMN_MOVIE_ID + ")" + " VALUES ('" + userID + "', '" + movieID + "');";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }

    public void resetDismissedByUserID(String userID) {
        String query = "DELETE FROM " + TABLE_DISMISSED + " WHERE " + COLUMN_USER_ID + "='" + userID + "';";
        Log.d("de", query);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.close();
    }


    public ArrayList<MovieModel> findMovieByTitle(String movieTitle, String userID) {

        String query = "SELECT * FROM " + TABLE_MOVIES + " WHERE " +
                COLUMN_MOVIE_TITLE + " LIKE '%" + movieTitle + "%' AND " + TABLE_MOVIES + "." + COLUMN_MOVIE_ID + " NOT IN (SELECT " +
        COLUMN_MOVIE_ID + " FROM " + TABLE_DISMISSED + " WHERE " + COLUMN_USER_ID + "='" + userID + "');";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String[] columns = new String[]{COLUMN_MOVIE_ID, COLUMN_MOVIE_TITLE, COLUMN_MOVIE_RELEASE_DATE, COLUMN_MOVIE_RATING, COLUMN_MOVIE_OVERVIEW, COLUMN_MOVIE_POSTER_PATH};
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);


        queryDataBase(cursor, sqLiteDatabase, query);

            sqLiteDatabase.close();
            return movies;
        }

    public ArrayList<MovieModel> findMovieByTitleAndGenre(String movieTitle, String movieGenres, String userID) {
        Log.d("de", "OK genre");
        String movieId = "";
//        String query = "SELECT * FROM " + TABLE_MOVIES + " JOIN "+ TABLE_MOVIE_GENRES + " WHERE " +
//                COLUMN_MOVIE_TITLE + " LIKE '%" + movieTitle + "%' AND "+ TABLE_MOVIES+"." + COLUMN_MOVIE_ID + " = "+ TABLE_MOVIE_GENRES+"." + COLUMN_GENRE_MOVIE_ID +
//                " AND " + TABLE_MOVIE_GENRES+"."+ COLUMN_GENRE + " IN (" + movieGenres + ")";
        String query = "SELECT * FROM " + TABLE_MOVIES + " JOIN "+ TABLE_MOVIE_GENRES + " WHERE " +
                COLUMN_MOVIE_TITLE + " LIKE '%" + movieTitle + "%' AND "+ TABLE_MOVIES+"." + COLUMN_MOVIE_ID + " = "+ TABLE_MOVIE_GENRES+"." + COLUMN_GENRE_MOVIE_ID +
                " AND " + COLUMN_GENRE + " IN (" + movieGenres + ") AND " + TABLE_MOVIES + "." + COLUMN_MOVIE_ID + " NOT IN (SELECT " +
                COLUMN_MOVIE_ID + " FROM " + TABLE_DISMISSED + " WHERE " + COLUMN_USER_ID + "='" + userID + "') GROUP BY " + COLUMN_MOVIE_TITLE;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String[] columns = new String[]{COLUMN_MOVIE_ID, COLUMN_MOVIE_TITLE, COLUMN_MOVIE_RELEASE_DATE, COLUMN_MOVIE_RATING, COLUMN_MOVIE_OVERVIEW, COLUMN_MOVIE_POSTER_PATH};
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        MovieModel movie;

        queryDataBase(cursor, sqLiteDatabase, query);

        sqLiteDatabase.close();
        return movies;
    }

        public void queryDataBase(Cursor cursor, SQLiteDatabase sqLiteDatabase, String query){
            String movieId = "";
            MovieModel movie;
            String genreQuery;

            if (cursor.getCount() <= 1) {
                movie = new MovieModel();
                if (cursor.moveToFirst()) {
                    cursor.moveToFirst();
                    movie.setMovieID(cursor.getString(0));
                    movieId = cursor.getString(0);
                    movie.setTitle(cursor.getString(1));
                    movie.setReleaseDate(cursor.getString(2));
                    movie.setDirectors(cursor.getString(3));
                    movie.setVoteAverage(Float.parseFloat(cursor.getString(4)));
                    movie.setMovieOverview(cursor.getString(5));
                    movie.setPosterPath(cursor.getString(6));
                } else {
                    movie = null;
                }

                Log.d("de", "Movie ID is: " + movieId);

                genreQuery = new StringBuilder().append("SELECT * FROM ").append(TABLE_MOVIE_GENRES).append(" WHERE ").append(COLUMN_GENRE_MOVIE_ID).append(" = '").append(movieId).append("';").toString();


                cursor = sqLiteDatabase.rawQuery(genreQuery, null);
                if (cursor.moveToFirst()) {
                    cursor.moveToFirst();
                    movie.addGenres(cursor.getString(1));
                    int i = 1;
                    while (cursor.move(i++)) {
                        movie.addGenres(cursor.getString(1));
                    }

                    movies.add(movie);
                }
            } else {
                int totalRows = cursor.getCount();
                for (int i = 0; i < totalRows; i++) {
                    movie = new MovieModel();
                    cursor = sqLiteDatabase.rawQuery(query, null);
                    if (cursor.moveToFirst()) {
                        cursor.move(i);
                        movie.setMovieID(cursor.getString(0));
                        movieId = cursor.getString(0);
                        movie.setTitle(cursor.getString(1));
                        movie.setReleaseDate(cursor.getString(2));
                        movie.setDirectors(cursor.getString(3));
                        movie.setVoteAverage(Float.parseFloat(cursor.getString(4)));
                        movie.setMovieOverview(cursor.getString(5));
                        movie.setPosterPath(cursor.getString(6));
                    } else {
                        movie = null;
                    }

                    genreQuery = new StringBuilder().append("SELECT * FROM ").append(TABLE_MOVIE_GENRES).append(" WHERE ").append(COLUMN_GENRE_MOVIE_ID).append(" = '").append(movieId).append("';").toString();

                    cursor = sqLiteDatabase.rawQuery(genreQuery, null);
                    if (cursor.moveToFirst()) {
                        cursor.moveToFirst();
                        movie.addGenres(cursor.getString(1));
                        int j = 1;
                        while (cursor.move(j++)) {
                            movie.addGenres(cursor.getString(1));
                        }
                    }
                    movies.add(new MovieModel(movie.getMovieID(), movie.getTitle(), movie.getReleaseDate(), movie.getDirectors(), movie.getVoteAverage(), movie.getMovieOverview(), movie.getPosterPath(), movie.getGenres()));
//                    Log.d("de", "Added movie in Movies ArrayList" + movies.get(movies.size()-1).getTitle()+ " ---");
                }
            }
        }



    }



    /*
    SQL CODE TO CREATE TABLE movies
    CREATE TABLE movies (
            movie_id INTEGER NOT NULL,
            movie_title VARCHAR NOT NULL,
            movie_release_date DATE NOT NULL,
            movie_rating DOUBLE NOT NULL,
            movie_overview VARCHAR NOT NULL,
            movie_poster_path VARCHAR NOT NULL,
            PRIMARY KEY (movie_id)
    );

    SQL CODE TO CREATE TABLE movie_genres
    CREATE TABLE movie_genres (
        movie_id INT NOT NULL,
        genre VARCHAR NOT NULL,
        PRIMARY KEY (movie_id, genre)
        FOREIGN KEY (movie_id) REFERENCES movie(movie_id)
    );

    SQL CODE TO INSERT VALUES INTO TABLE movies
    INSERT INTO movies (movie_title, movie_release_date, movie_rating, movie_overview, movie_poster_path)
    VALUES
      ('The Shawshank Redemption', '1994/09/10', 9.2, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', '/res/drawable/images/shawshank.png'),
      ('The Godfather', '1972/03/14', 9.2, 'The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.', '/res/drawable/images/godfather.png'),
      ('The Dark Knight', '2008/07/14', 9.0, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', '/res/drawable/images/darkknight.png'),
      ('The Godfather: Part II', '1972/03/14', 9.0, 'The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.', '/res/drawable/images/godfather2.png'),
      ('12 Angry Men', '1957/4/10', 9.0, 'The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.', '/res/drawable/images/twelveangrymen.png'),
      ("Schindler's List", '1993/00/00', 9.0, 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', '/res/drawable/images/schindlerslist.png');

    SQL CODE TO INSERT VALUES INTO TABLE movie-genres
    INSERT INTO movie_genres
    VALUES
        (1,'Drama'),
        (2, 'Drama'),
        (3, 'Action'),
        (3, 'Drama'),
        (4, 'Drama'),
        (5, 'Drama'),
        (6, 'Drama');
     */
