package ohtu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.http.client.fluent.Request;

public class Main {
  public static void main(String[] args) throws IOException {
    String url = "https://nhlstatisticsforohtu.herokuapp.com/players";

    String bodyText = Request.Get(url).execute().returnContent().asString();

    Gson mapper = new Gson();
    List<Player> players = mapper.fromJson(bodyText, new TypeToken<List<Player>>(){}.getType());
    players = players.stream()
        .filter(player -> player.getNationality().equals("FIN"))
        .sorted((p1, p2) -> p2.getGoals() + p2.getAssists() - p1.getGoals() - p1.getAssists()) //reverse order
        .collect(Collectors.toList());

    System.out.println("Players from FIN " + ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "\n");
    players.forEach(System.out::println);
  }
}
