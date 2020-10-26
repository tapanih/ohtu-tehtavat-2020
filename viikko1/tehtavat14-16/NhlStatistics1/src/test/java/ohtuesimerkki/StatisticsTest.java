package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {

    Reader readerStub = () -> {
        ArrayList<Player> players = new ArrayList<>();

        players.add(new Player("Semenko", "EDM", 4, 12));
        players.add(new Player("Lemieux", "PIT", 45, 54));
        players.add(new Player("Kurri",   "EDM", 37, 53));
        players.add(new Player("Yzerman", "DET", 42, 56));
        players.add(new Player("Gretzky", "EDM", 35, 89));

        return players;
    };

    Statistics stats;

    @Before
    public void setUp(){
        stats = new Statistics(readerStub);
    }

    @Test
    public void searchReturnsPlayerIfItExists() {
        Player player = stats.search("Gretzky");

        assertEquals("Gretzky", player.getName());
        assertEquals("EDM", player.getTeam());
    }

    @Test
    public void searchReturnsNullIfPlayerDoesNotExists() {
        Player player = stats.search("Selänne");

        assertNull(player);
    }

    @Test
    public void teamReturnsCorrectAmountOfPlayers() {
        List<Player> team = stats.team("EDM");

        assertEquals(3, team.size());
    }

    @Test
    public void topScorersReturnsCorrectPlayers() {
        List<Player> players = stats.topScorers(3);

        assertEquals(3, players.size());
        assertEquals("Gretzky", players.get(0).getName());
        assertEquals("Lemieux", players.get(1).getName());
        assertEquals("Yzerman", players.get(2).getName());
    }
}
