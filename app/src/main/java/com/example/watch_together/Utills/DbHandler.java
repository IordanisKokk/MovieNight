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
        String CREATE_MOVIE_GENRES_TABLE = "CREATE TABLE " + TABLE_MOVIE_GENRES + " (" + COLUMN_MOVIE_ID + " VARCHAR(20) NOT NULL, " + COLUMN_GENRE + " VARCHAR NOT NULL, PRIMARY KEY (" + COLUMN_MOVIE_ID + ", " + COLUMN_GENRE + "), FOREIGN KEY (" + COLUMN_MOVIE_ID + ") REFERENCES " + TABLE_MOVIES + "(" + COLUMN_MOVIE_ID + "));";
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

        String INSERT_INTO_MOVIE_GENRES = "INSERT INTO " + TABLE_MOVIE_GENRES + " VALUES\n" +
                "('tt0111161', 'Drama'),\n" +
                "('tt0068646', 'Crime'),\n" +
                "('tt0068646', 'Drama'),\n" +
                "('tt0468569', 'Action'),\n" +
                "('tt0468569', 'Crime'),\n" +
                "('tt0468569', 'Drama'),\n" +
                "('tt0071562', 'Crime'),\n" +
                "('tt0071562', 'Drama'),\n" +
                "('tt0050083', 'Crime'),\n" +
                "('tt0050083', 'Drama'),\n" +
                "('tt0108052', 'Biography'),\n" +
                "('tt0108052', 'Drama'),\n" +
                "('tt0108052', 'History'),\n" +

                // Movies 100-149
                "('tt0211915', 'Comedy'),\n" +
                "('tt0211915', 'Romance'),\n" +
                "('tt0066921', 'Crime'),\n" +
                "('tt0066921', 'Sci-Fi'),\n" +
                "('tt0091251', 'Drama'),\n" +
                "('tt0091251', 'Thriller'),\n" +
                "('tt0091251', 'War'),\n" +
                "('tt0093058', 'Drama'),\n" +
                "('tt0093058', 'War'),\n" +
                "('tt0036775', 'Crime'),\n" +
                "('tt0036775', 'Drama'),\n" +
                "('tt0036775', 'Film-Noir'),\n" +
                "('tt0053604', 'Comedy'),\n" +
                "('tt0053604', 'Drama'),\n" +
                "('tt0053604', 'Romance'),\n" +
                "('tt0086250', 'Crime'),\n" +
                "('tt0086250', 'Drama'),\n" +
                "('tt0044741', 'Drama'),\n" +
                "('tt0056592', 'Crime'),\n" +
                "('tt0056592', 'Drama'),\n" +
                "('tt0070735', 'Comedy'),\n" +
                "('tt0070735', 'Crime'),\n" +
                "('tt0070735', 'Drama'),\n" +
                "('tt0075314', 'Crime'),\n" +
                "('tt0075314', 'Drama'),\n" +
                "('tt0119488', 'Crime'),\n" +
                "('tt0119488', 'Drama'),\n" +
                "('tt0119488', 'Mystery'),\n" +
                "('tt1049413', 'Animation'),\n" +
                "('tt1049413', 'Adventure'),\n" +
                "('tt1049413', 'Comedy'),\n" +
                "('tt0113277', 'Action'),\n" +
                "('tt0113277', 'Crime'),\n" +
                "('tt0113277', 'Drama'),\n" +
                "('tt8503618', 'Biography'),\n" +
                "('tt8503618', 'Drama'),\n" +
                "('tt8503618', 'History'),\n" +
                "('tt6710474', 'Action'),\n" +
                "('tt6710474', 'Adventure'),\n" +
                "('tt6710474', 'Comedy'),\n" +
                "('tt0017136', 'Drama'),\n" +
                "('tt0017136', 'Sci-Fi'),\n" +
                "('tt0208092', 'Comedy'),\n" +
                "('tt0208092', 'Crime'),\n" +
                "('tt1832382', 'Drama'),\n" +
                "('tt0095016', 'Action'),\n" +
                "('tt0095016', 'Thriller'),\n" +
                "('tt10872600', 'Action'),\n" +
                "('tt10872600', 'Adventure'),\n" +
                "('tt10872600', 'Fantasy'),\n" +
                "('tt1255953', 'Drama'),\n" +
                "('tt1255953', 'Mystery'),\n" +
                "('tt0097576', 'Action'),\n" +
                "('tt0097576', 'Adventure'),\n" +
                "('tt0040522', 'Drama'),\n" +
                "('tt8579674', 'Action'),\n" +
                "('tt8579674', 'Drama'),\n" +
                "('tt8579674', 'War'),\n" +
                "('tt0986264', 'Drama'),\n" +
                "('tt0986264', 'Family'),\n" +
                "('tt0363163', 'Biography'),\n" +
                "('tt0363163', 'Drama'),\n" +
                "('tt0363163', 'History'),\n" +
                "('tt0059578', 'Western'),\n" +
                "('tt0372784', 'Action'),\n" +
                "('tt0372784', 'Crime'),\n" +
                "('tt0372784', 'Drama'),\n" +
                "('tt5074352', 'Action'),\n" +
                "('tt5074352', 'Biography'),\n" +
                "('tt5074352', 'Drama'),\n" +
                "('tt0012349', 'Comedy'),\n" +
                "('tt0012349', 'Drama'),\n" +
                "('tt0012349', 'Family'),\n" +
                "('tt0053291', 'Comedy'),\n" +
                "('tt0053291', 'Music'),\n" +
                "('tt0053291', 'Romance'),\n" +
                "('tt10272386', 'Drama'),\n" +
                "('tt10272386', 'Mystery'),\n" +
                "('tt0042192', 'Drama'),\n" +
                "('tt6966692', 'Biography'),\n" +
                "('tt6966692', 'Comedy'),\n" +
                "('tt6966692', 'Drama'),\n" +
                "('tt0993846', 'Biography'),\n" +
                "('tt0993846', 'Comedy'),\n" +
                "('tt0993846', 'Crime'),\n" +
                "('tt0055031', 'Drama'),\n" +
                "('tt0055031', 'War'),\n" +
                "('tt0105695', 'Drama'),\n" +
                "('tt0105695', 'Western'),\n" +
                "('tt0112641', 'Crime'),\n" +
                "('tt0112641', 'Drama'),\n" +
                "('tt0089881', 'Action'),\n" +
                "('tt0089881', 'Drama'),\n" +
                "('tt0089881', 'War'),\n" +
                "('tt0457430', 'Drama'),\n" +
                "('tt0457430', 'Fantasy'),\n" +
                "('tt0457430', 'War'),\n" +
                "('tt0469494', 'Drama'),\n" +
                "('tt0167404', 'Drama'),\n" +
                "('tt0167404', 'Mystery'),\n" +
                "('tt0167404', 'Thriller'),\n" +
                "('tt0268978', 'Biography'),\n" +
                "('tt0268978', 'Drama'),\n" +
                "('tt0071853', 'Adventure'),\n" +
                "('tt0071853', 'Comedy'),\n" +
                "('tt0071853', 'Fantasy'),\n" +
                "('tt0120382', 'Comedy'),\n" +
                "('tt0120382', 'Drama'),\n" +
                "('tt0055630', 'Action'),\n" +
                "('tt0055630', 'Drama'),\n" +
                "('tt0055630', 'Thriller'),\n" +
                "('tt0040897', 'Adventure'),\n" +
                "('tt0040897', 'Drama'),\n" +
                "('tt0040897', 'Western'),\n" +
                "('tt0042876', 'Crime'),\n" +
                "('tt0042876', 'Drama'),\n" +
                "('tt0042876', 'Mystery'),\n" +
                "('tt0057115', 'Adventure'),\n" +
                "('tt0057115', 'Drama'),\n" +
                "('tt0057115', 'History');";

        String INSERT_INTO_MOVIES = "INSERT INTO movies (" + COLUMN_MOVIE_ID + ", " + COLUMN_MOVIE_TITLE + ", " + COLUMN_MOVIE_RELEASE_DATE + ", " + COLUMN_MOVIE_DIRECTORS + ", " + COLUMN_MOVIE_RATING + ", " + COLUMN_MOVIE_OVERVIEW + ", " + COLUMN_MOVIE_POSTER_PATH + ")\n" + " VALUES\n" +
                "('tt0111161', 'The Shawshank Redemption', '1994/09/10', 'Frank Darabont', 9.2, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'poster_tt0111161'),\n" +
                "('tt0068646', 'The Godfather', '1972/03/14', 'Francis Ford Coppola', 9.2, 'The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.', 'poster_tt0068646'),\n" +
                "('tt0468569', 'The Dark Knight', '2008/07/14', 'Christopher Nolan', 9.0, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'poster_tt0468569'),\n" +
                "('tt0071562', 'The Godfather: Part II', '1972/03/14', 'Francis Ford Coppola', 9.0, 'The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.', 'poster_tt0071562'),\n" +
                "('tt0050083', '12 Angry Men', '1957/4/10', 'Reginald Rose', 9.0, 'The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.', 'poster_tt0050083'),\n" +
                "('tt0108052', \"Schindler's List\", '1993/00/00', 'Steven Spielberg', 9.0, 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 'poster_tt0050083'),\n" +

                // Movies 100-149
                "('tt0211915', 'Amelie', '', 'Jean-Pierre Jeunet', 8.2, 'Amélie is an innocent and naive girl in Paris with her own sense of justice. She decides to help those around her and, along the way, discovers love.', 'poster_tt0211915'),\n" +
                "('tt0066921', 'A Clockwork Orange', '', 'Stanley Kubrick', 8.2, \"In the future, a sadistic gang leader is imprisoned and volunteers for a conduct-aversion experiment, but it doesn't go as planned.\", 'poster_tt0066921'),\n" +
                "('tt0091251', 'Come and See', '', 'Elem Klimov', 8.2, 'After finding an old rifle, a young boy joins the Soviet resistance movement against ruthless German forces and experiences the horrors of World War II.', 'poster_tt0091251'),\n" +
                "('tt0093058', 'Full Metal Jacket', '', 'Stanley Kubrick', 8.2, 'A pragmatic U.S. Marine observes the dehumanizing effects the Vietnam War has on his fellow recruits from their brutal boot camp training to the bloody street fighting in Hue.', 'poster_tt0093058'),\n" +
                "('tt0036775', 'Double Indemnity', '', 'Billy Wilder', 8.2, 'A Los Angeles insurance representative lets an alluring housewife seduce him into a scheme of insurance fraud and murder that arouses the suspicion of his colleague, an insurance investigator.', 'poster_tt0036775'),\n" +
                "('tt0053604', 'The Apartment', '', 'Billy Wilder', 8.2, 'A Manhattan insurance clerk tries to rise in his company by letting its executives use his apartment for trysts, but complications and a romance of his own ensue.', 'poster_tt0053604'),\n" +
                "('tt0086250', 'Scarface', '', 'Brian De Palma', 8.2, 'In 1980 Miami, a determined Cuban immigrant takes over a drug cartel and succumbs to greed.', 'poster_tt0086250'),\n" +
                "('tt0044741', 'Ikiru', '', 'Akira Kurosawa', 8.2, 'A bureaucrat tries to find meaning in his life after he discovers he has terminal cancer.', 'poster_tt0044741'),\n" +
                "('tt0056592', 'To Kill a Mockingbird', 'Robert Mulligan', '', 8.2, 'Atticus Finch, a widowed lawyer in Depression-era Alabama, defends a black man against an undeserved rape charge, and his children against prejudice.', 'poster_tt0056592'),\n" +
                "('tt0070735', 'The Sting', '', 'George Roy Hill', 8.2, 'Two grifters team up to pull off the ultimate con.', 'poster_tt0070735'),\n" +
                "('tt0075314', 'Taxi Driver', '', 'Martin Scorsese', 8.2, 'A mentally unstable veteran works as a nighttime taxi driver in New York City, where the perceived decadence and sleaze fuels his urge for violent action.', 'poster_tt0075314'),\n" +
                "('tt0119488', 'L.A. Confidential', '', 'Curtis Hanson', 8.2, 'As corruption grows in 1950s Los Angeles, three policemen - one strait-laced, one brutal, and one sleazy - investigate a series of murders with their own brand of justice.', 'poster_tt0119488'),\n" +
                "('tt1049413', 'Up', '', 'Pete Docter, Bob Peterson', 8.2, '78-year-old Carl Fredricksen travels to Paradise Falls in his house equipped with balloons, inadvertently taking a young stowaway.', 'poster_tt1049413'),\n" +
                "('tt0113277', 'Heat', '', 'Michael Mann', 8.2, 'A group of high-end professional thieves start to feel the heat from the LAPD when they unknowingly leave a clue at their latest heist.', 'poster_tt0113277'),\n" +
                "('tt8503618', 'Hamilton', '', 'Thomas Kail', 8.2, \"The real life of one of America's foremost founding fathers and first Secretary of the Treasury, Alexander Hamilton. Captured live on Broadway from the Richard Rodgers Theater with the original Broadway cast.\", 'poster_tt8503618'),\n" +
                "('tt6710474', 'Everything Everywhere All at Once', '', '    Dan Kwan, Daniel Scheinert', 8.2, 'An aging Chinese immigrant is swept up in an insane adventure, where she alone can save the world by exploring other universes connecting with the lives she could have led.', 'poster_tt6710474'),\n" +
                "('tt0017136', 'Metropolis', '', '    Fritz Lang', 8.2, \"In a futuristic city sharply divided between the working class and the city planners, the son of the city's mastermind falls in love with a working-class prophet who predicts the coming of a savior to mediate their differences.\", 'poster_tt0017136'),\n" +
                "('tt0208092', 'Snatch', '', '    Guy Ritchie', 8.2, 'Unscrupulous boxing promoters, violent bookmakers, a Russian gangster, incompetent amateur robbers and supposedly Jewish jewelers fight to track down a priceless stolen diamond.', 'poster_tt0208092'),\n" +
                "('tt1832382', 'A Separation', '', '    Asghar Farhadi', 8.2, \"A married couple are faced with a difficult decision - to improve the life of their child by moving to another country or to stay in Iran and look after a deteriorating parent who has Alzheimer's disease.\", 'poster_tt1832382'),\n" +
                "('tt0095016', 'Die Hard', '', '    John McTiernan', 8.2, 'An NYPD officer tries to save his wife and several others taken hostage by German terrorists during a Christmas party at the Nakatomi Plaza in Los Angeles.', 'poster_tt0095016'),\n" +
                "('tt10872600', 'Spider-Man: No Way Home', '', '    Jon Watts', 8.2, \"With Spider-Man's identity now revealed, Peter asks Doctor Strange for help. When a spell goes wrong, dangerous foes from other worlds start to appear, forcing Peter to discover what it truly means to be Spider-Man.\", 'poster_tt10872600'),\n" +
                "('tt1255953', 'Incendies', '', '    Denis Villeneuve', 8.2, \"Twins journey to the Middle East to discover their family history and fulfill their mother's last wishes.\", 'poster_tt1255953'),\n" +
                "('tt0097576', 'Indiana Jones and the Last Crusade', '', '    Steven Spielberg', 8.2, \"In 1938, after his father Professor Henry Jones, Sr. goes missing while pursuing the Holy Grail, Professor Henry 'Indiana'' Jones, Jr. finds himself up against Adolf Hitler's Nazis again to stop them from obtaining its powers.\", 'poster_tt0097576'),\n" +
                "('tt0040522', 'Bicycle Thieves', '', '    Vittorio De Sica', 8.2, \"In post-war Italy, a working-class man's bicycle is stolen, endangering his efforts to find work. He and his son set out to find it.\", 'poster_tt0040522'),\n" +
                "('tt8579674', '1917', '', '    Sam Mendes', 8.2, 'April 6th, 1917. As an infantry battalion assembles to wage war deep in enemy territory, two soldiers are assigned to race against time and deliver a message that will stop 1,600 men from walking straight into a deadly trap.', 'poster_tt8579674'),\n" +
                "('tt0986264', 'Like Stars on Earth', '', '    Aamir Khan, Amole Gupte', 8.2, 'An eight-year-old boy is thought to be a lazy trouble-maker, until the new art teacher has the patience and compassion to discover the real problem behind his struggles in school.', 'poster_tt0986264'),\n" +
                "('tt0363163', 'Downfall', '', '    Oliver Hirschbiegel', 8.2, \"Traudl Junge, the final secretary for Adolf Hitler, tells of the Nazi dictator's final days in his Berlin bunker at the end of WWII.\", 'poster_tt0363163'),\n" +
                "('tt0059578', 'For a Few Dollars More', '', '    Sergio Leone', 8.2, 'Two bounty hunters with the same intentions team up to track down an escaped Mexican outlaw.', 'poster_tt0059578'),\n" +
                "('tt0372784', 'Batman Begins', '', '    Christopher Nolan', 8.2, 'After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from corruption.', 'poster_tt0372784'),\n" +
                "('tt5074352', 'Dangal', '', '    Nitesh Tiwari', 8.2, 'Former wrestler Mahavir Singh Phogat and his two wrestler daughters struggle towards glory at the Commonwealth Games in the face of societal oppression.', 'poster_tt5074352'),\n" +
                "('tt0012349', 'The Kid', '', '    Charles Chaplin', 8.2, 'The Tramp cares for an abandoned child, but events put their relationship in jeopardy.', 'poster_tt0012349'),\n" +
                "('tt0053291', 'Some Like It Hot', '', '    Billy Wilder', 8.2, 'After two male musicians witness a mob hit, they flee the state in an all-female band disguised as women, but further complications set in.', 'poster_tt0053291'),\n" +
                "('tt10272386', 'The Father', '', '    Florian Zeller', 8.2, 'A man refuses all assistance from his daughter as he ages. As he tries to make sense of his changing circumstances, he begins to doubt his loved ones, his own mind and even the fabric of his reality.', 'poster_tt10272386'),\n" +
                "('tt0042192', 'All About Eve', '', '    Joseph L. Mankiewicz', 8.2, 'A seemingly timid but secretly ruthless ingénue insinuates herself into the lives of an aging Broadway star and her circle of theater friends.', 'poster_tt0042192'),\n" +
                "('tt6966692', 'Green Book', '', '    Peter Farrelly', 8.2, 'A working-class Italian-American bouncer becomes the driver of an African-American classical pianist on a tour of venues through the 1960s American South.', 'poster_tt6966692'),\n" +
                "('tt0993846', 'The Wolf of Wall Street', '', '    Martin Scorsese', 8.2, 'Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.', 'poster_tt0993846'),\n" +
                "('tt0055031', 'Judgment at Nuremberg', '', '    Stanley Kramer', 8.2, 'In 1948, an American court in occupied Germany tries four Nazis judged for war crimes.', 'poster_tt0055031'),\n" +
                "('tt0105695', 'Unforgiven', '', '    Clint Eastwood', 8.2, 'Retired Old West gunslinger William Munny reluctantly takes on one last job, with the help of his old partner Ned Logan and a young man, The \"Schofield Kid.\"', 'poster_tt0105695'),\n" +
                "('tt0112641', 'Casino', '', '    Martin Scorsese', 8.2, 'A tale of greed, deception, money, power, and murder occur between two best friends: a mafia enforcer and a casino executive compete against each other over a gambling empire, and over a fast-living and fast-loving socialite.', 'poster_tt0112641'),\n" +
                "('tt0089881', 'Ran', '', '    Akira Kurosawa', 8.2, 'In Medieval Japan, an elderly warlord retires, handing over his empire to his three sons. However, he vastly underestimates how the new-found power will corrupt them and cause them to turn on each other...and him.', 'poster_tt0089881'),\n" +
                "('tt0457430', \"Pan's Labyrinth\", '', '    Guillermo del Toro', 8.2, 'In the Falangist Spain of 1944, the bookish young stepdaughter of a sadistic army officer escapes into an eerie but captivating fantasy world.', 'poster_tt0457430'),\n" +
                "('tt0469494', 'There Will Be Blood', '', '    Paul Thomas Anderson', 8.2, 'A story of family, religion, hatred, oil and madness, focusing on a turn-of-the-century prospector in the early days of the business.', 'poster_tt0469494'),\n" +
                "('tt0167404', 'The Sixth Sense', '', '    M. Night Shyamalan', 8.2, 'A frightened, withdrawn Philadelphia boy who communicates with spirits seeks the help of a disheartened child psychologist.', 'poster_tt0167404'),\n" +
                "('tt0268978', 'A Beautiful Mind', '', '    Ron Howard', 8.2, 'After John Nash, a brilliant but asocial mathematician, accepts secret work in cryptography, his life takes a turn for the nightmarish.', 'poster_tt0268978'),\n" +
                "('tt0071853', 'Monty Python and the Holy Grail', '', '    Terry Gilliam, Terry Jones', 8.2, 'King Arthur and his Knights of the Round Table embark on a surreal, low-budget search for the Holy Grail, encountering many, very silly obstacles.', 'poster_tt0071853'),\n" +
                "('tt0120382', 'The Truman Show', '', '    Peter Weir', 8.2, 'An insurance salesman discovers his whole life is actually a reality TV show.', 'poster_tt0120382'),\n" +
                "('tt0055630', 'Yojimbo', '', '    Akira Kurosawa', 8.1, 'A crafty ronin comes to a town divided by two criminal gangs and decides to play them against each other to free the town.', 'poster_tt0055630'),\n" +
                "('tt0040897', 'The Treasure of the Sierra Madre', '', '    John Huston', 8.1, 'Two down-on-their-luck Americans searching for work in 1920s Mexico convince an old prospector to help them mine for gold in the Sierra Madre Mountains.', 'poster_tt0040897'),\n" +
                "('tt0042876', 'Rashomon', '', '    Akira Kurosawa', 8.1, \"The rape of a bride and the murder of her samurai husband are recalled from the perspectives of a bandit, the bride, the samurai's ghost and a woodcutter.\", 'poster_tt0042876'),\n" +
                "('tt0057115', 'The Great Escape', '', '    John Sturges', 8.1, 'Allied prisoners of war plan for several hundred of their number to escape from a German camp during World War II.', 'poster_tt0057115');";

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

            String genreQuery = new StringBuilder().append("SELECT * FROM ").append(TABLE_MOVIE_GENRES).append(" WHERE ").append(COLUMN_GENRE_MOVIE_ID).append(" = '").append(movie.getMovieID()).append("';").toString();

            Cursor genreCursor = sqLiteDatabase.rawQuery(genreQuery, null);
            while (genreCursor.moveToNext()) {
                movie.addGenres(genreCursor.getString(1));
            }

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
                while (cursor.moveToNext()) {
                    movie.addGenres(cursor.getString(1));
                }
                movies.add(movie);
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
                    while (cursor.moveToNext()) {
                        movie.addGenres(cursor.getString(1));
                    }
                    movies.add(movie);
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
