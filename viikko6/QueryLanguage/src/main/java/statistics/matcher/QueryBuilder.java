package statistics.matcher;

public class QueryBuilder {
  Matcher matcher = new All();

  public QueryBuilder playsIn(String team) {
    matcher = new And(matcher, new PlaysIn(team));
    return this;
  }

  public QueryBuilder hasAtLeast(int value, String category) {
    matcher = new And(matcher, new HasAtLeast(value, category));
    return this;
  }

  public QueryBuilder hasFewerThan(int value, String category) {
    matcher = new And(matcher, new HasFewerThan(value, category));
    return this;
  }

  public Matcher build() {
    return matcher;
  }
}
