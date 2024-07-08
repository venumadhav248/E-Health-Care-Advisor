import java.sql.*;
import java.util.Scanner;

class ConnectDB {
    Statement stmt;
    Connection con;

    void createTeams() throws SQLException, ClassNotFoundException{
        String query = "CREATE TABLE teams (team_id INT PRIMARY KEY, teamname VARCHAR2(25) NOT NULL," +
                " homeGround VARCHAR2(25) NOT NULL, coachName VARCHAR2(25) NOT NULL)";
        stmt.executeUpdate(query);
    }

    void insertIntoTeams(int team_id, String teamname, String homeground, String coachName) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO teams (team_id, teamname, homeground, coachName) VALUES (" +
                team_id + ", '" + teamname + "', '" + homeground + "', '" + coachName + "')";
        stmt.executeUpdate(query);
    }

    void createCoach() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE coach (team_id INT NOT NULL, coachId INT PRIMARY KEY, " +
                "coachName VARCHAR2(25) NOT NULL, Experience NUMBER(2) NOT NULL, coachAge NUMBER(2) NOT NULL)";

        stmt.executeUpdate(query);
    }

    void insertIntoCoach(int team_id, int coachId, String coachName, int experience, int coachAge) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO coach (team_id, coachId, coachName, Experience, coachAge) VALUES (" 
                + team_id + ", " + coachId + ", '" + coachName + "', " + experience + ", " + coachAge + ")";
        stmt.executeUpdate(query);
    }

    void createMatches() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE matches (match_id INT PRIMARY KEY, home_team_id INT NOT NULL," +
                "Ground VARCHAR2(25) NOT NULL, Match_Date VARCHAR2(10) NOT NULL, Away_team_id INT NOT NULL)";
        stmt.executeUpdate(query);
    }

    void insertIntoMatches(int matchId, int home_team_id, String Ground, String Match_Date, int Away_team_id) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO matches (match_id, home_team_id, Ground, Match_Date, Away_team_id) VALUES ("
                + matchId + ", " + home_team_id + ", '" + Ground + "', '" + Match_Date + "', " + Away_team_id + ")";
        stmt.executeUpdate(query);
    }

    void createPlayers() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE players (player_id INT PRIMARY KEY," +
                " player_name VARCHAR2(25) NOT NULL, position VARCHAR2(20) NOT NULL, team_id INT NOT NULL)";
        stmt.executeUpdate(query);
    }

    void insertIntoPlayers(int player_id, String player_name, String position, int team_id) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO players (player_id, player_name, position, team_id) VALUES ("
                + player_id + ", '" + player_name + "', '" + position + "', " + team_id + ")";
        stmt.executeUpdate(query);
    }

    void createScore() throws SQLException, ClassNotFoundException {
        String query = "CREATE TABLE score (match_id INT NOT NULL, home_score INT NOT NULL," +
                " away_score INT NOT NULL, win_team VARCHAR2(20) NOT NULL)";
        stmt.executeUpdate(query);
    }

    void insertIntoScore(int match_id, int home_score, int away_score, String win_team) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO score (match_id, home_score, away_score, win_team) VALUES ("
                + match_id + ", " + home_score + ", " + away_score + ", '" + win_team + "')";
        stmt.executeUpdate(query);
    }

    ConnectDB() throws SQLException, ClassNotFoundException {
        String username = "system";
        String password = "venu24";
        Class.forName("oracle.jdbc.driver.OracleDriver");
       con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:xe", username, password);
       stmt = con.createStatement();
    }
}

public class casestudy {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectDB obj = new ConnectDB();
        try (Scanner sc = new Scanner(System.in)) {
            int teamId, coachId, experience, coachAge, matchId, homeTeamId, awayTeamId, playerId, homeScore, awayScore;
            String teamname, homeGround, coachName, matchDate, playerName, winTeam, ground, position;
            int i;
            obj.createTeams();
            obj.createCoach();
            obj.createMatches();
            obj.createPlayers();
            obj.createScore();

            for (i = 0; i < 3; i++) {
                System.out.print("Enter Team ID: ");
                teamId = sc.nextInt();
                System.out.print("Enter Team Name: ");
                teamname = sc.next();

                System.out.print("Enter Coach ID: ");
                coachId = sc.nextInt();
                System.out.print("Enter Coach Name: ");
                coachName = sc.next();
                System.out.print("Enter Coach Age: ");
                coachAge = sc.nextInt();
                System.out.print("Enter Coach Experience: ");
                experience = sc.nextInt();

                System.out.print("Enter Player ID: ");
                playerId = sc.nextInt();
                System.out.print("Enter Player Name: ");
                playerName = sc.next();
                System.out.print("Enter Player Position: ");
                position = sc.next();

                System.out.print("Enter Home Ground: ");
                homeGround = sc.next();

                System.out.print("Enter Match ID: ");
                matchId = sc.nextInt();
                System.out.print("Enter Match Date: ");
                matchDate = sc.next();
                System.out.print("Enter Home Team ID: ");
                homeTeamId = sc.nextInt();
                System.out.print("Enter Away Team ID: ");
                awayTeamId = sc.nextInt();
                System.out.print("Enter Ground Location: ");
                ground = sc.next();

                System.out.print("Enter Winning Team: ");
                winTeam = sc.next();
                System.out.print("Enter Home Team Score: ");
                homeScore = sc.nextInt();
                System.out.print("Enter Away Team Score: ");
                awayScore = sc.nextInt();

                obj.insertIntoCoach(teamId, coachId, coachName, experience, coachAge);
                obj.insertIntoTeams(teamId, teamname, homeGround, coachName);
                obj.insertIntoMatches(matchId, homeTeamId, ground, matchDate, awayTeamId);
                obj.insertIntoPlayers(playerId, playerName, position, teamId);
                obj.insertIntoScore(matchId, homeScore, awayScore, winTeam);
            }
        }
    }
}
