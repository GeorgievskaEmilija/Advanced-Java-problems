package kolokviumski2code.FudbalskaTabela;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Team implements Comparable<Team>{
    String teamname;
    int loses;
    int wins;
    int draws;
    int givenGoals;
    int takenGoals;
    int points;
    int goalsdifference;

    public Team(String teamname) {
        this.teamname = teamname;
        loses = 0;
        wins = 0;
        draws = 0;
        givenGoals = 0;
        takenGoals = 0;
        points = 0;
        goalsdifference = 0;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getGivenGoals() {
        return givenGoals;
    }

    public void setGivenGoals(int givenGoals) {
        this.givenGoals = givenGoals;
    }

    public int getTakenGoals() {
        return takenGoals;
    }

    public void setTakenGoals(int takenGoals) {
        this.takenGoals = takenGoals;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsdifference() {
        return goalsdifference;
    }

    public void setGoalsdifference(int goalsdifference) {
        this.goalsdifference = goalsdifference;
    }

    @Override
    public int compareTo(Team o) {
        if (getPoints() == o.getPoints()){
            if (getGoalsdifference() == o.getGoalsdifference())
                return teamname.compareTo(o.teamname);
            else return -Integer.compare(getGoalsdifference(), o.getGoalsdifference());
        }
        return -Integer.compare(getPoints(), o.getPoints());
    }
    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d", teamname, wins + draws + loses, wins, draws, loses, points);
    }
}

class FootballTable {
    Map<String, Team> teams;

    public FootballTable() {
        teams = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        teams.putIfAbsent(homeTeam, new Team(homeTeam));
        Team doma = teams.get(homeTeam);
        doma.givenGoals += homeGoals;
        doma.takenGoals += awayGoals;

        teams.putIfAbsent(awayTeam, new Team(awayTeam));
        Team gostin = teams.get(awayTeam);
        gostin.givenGoals += awayGoals;
        gostin.takenGoals += homeGoals;

        if (homeGoals > awayGoals) {
            doma.wins++;
            gostin.loses++;
        }
        if (awayGoals > homeGoals) {
            gostin.wins++;
            doma.loses++;
        }
        if (homeGoals == awayGoals) {
            doma.draws++;
            gostin.draws++;
        }

    }

    public void updateParameters() {
        for (Team t : teams.values()) {
            t.points = t.wins * 3 + t.draws;
            t.goalsdifference = t.givenGoals - t.takenGoals;
        }
    }

    public void printTable() {
        updateParameters();
        List<Team> teamList = teams.values().stream().sorted(Team::compareTo).collect(Collectors.toList());
        int i = 1;
        for (Team t:teamList){
            System.out.println(String.format("%2d. %s", i, t));
            i++;
        }
    }
}

public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here

