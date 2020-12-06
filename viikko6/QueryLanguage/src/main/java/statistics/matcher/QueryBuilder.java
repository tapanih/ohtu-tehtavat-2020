package statistics.matcher;

import java.util.ArrayList;

public class QueryBuilder {
  private ArrayList<Matcher> matchers;

  public QueryBuilder() {
    matchers = new ArrayList<>();
    matchers.add(new All());
  }

  public QueryBuilder playsIn(String team) {
    matchers.add(new PlaysIn(team));
    return this;
  }

  public QueryBuilder hasAtLeast(int value, String category) {
    matchers.add(new HasAtLeast(value, category));
    return this;
  }

  public QueryBuilder hasFewerThan(int value, String category) {
    matchers.add(new HasFewerThan(value, category));
    return this;
  }

  public QueryBuilder oneOf(Matcher... matchers) {
    this.matchers.add(new Or(matchers));
    return this;
  }

  public Matcher build() {
    Matcher[] values = new Matcher[matchers.size()];
    for (int i = 0; i < matchers.size(); i++) {
      values[i] = matchers.get(i);
    }
    matchers = new ArrayList<>();
    matchers.add(new All());
    return new And(values);
  }
}
