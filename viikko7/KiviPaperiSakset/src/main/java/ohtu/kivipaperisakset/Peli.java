package ohtu.kivipaperisakset;

public class Peli {
  public static KPS uusiKaksinpeli() {
    return new KPSPelaajaVsPelaaja();
  }

  public static KPS uusiYksinpeli() {
    return new KPSTekoaly(new TekoalyHelppo());
  }

  public static KPS uusiPahaYksinpeli() {
    return new KPSTekoaly(new TekoalyParannettu(20));
  }
}
