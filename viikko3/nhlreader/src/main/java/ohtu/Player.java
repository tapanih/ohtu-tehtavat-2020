
package ohtu;

public class Player {
  private String name;
  private String nationality;
  private int assists;
  private int goals;
  private String team;

  public String getName() { return name; }

  public void setName(String name) {
    this.name = name;
  }

  public String getNationality() { return nationality; }

  public void setNationality(String nationality) { this.nationality = nationality; }

  public int getAssists() { return assists; }

  public void setAssists(int assists) { this.assists = assists; }

  public int getGoals() { return goals; }

  public void setGoals(int goals) { this.goals = goals; }

  public String getTeam() { return team; }

  public void setTeam(String team) { this.team = team; }

  @Override
  public String toString() {
    return String.format("%-20s  %s  %2d + %2d = %2d", name, team, goals, assists, (goals + assists));
  }
}
