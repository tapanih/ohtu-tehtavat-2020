package ohtu.kivipaperisakset;

import java.util.Scanner;

public abstract class KPS {
  protected static final Scanner scanner = new Scanner(System.in);

  private String ensimmaisenPelaajanSiirto() {
    System.out.println("Ensimmäisen pelaajan siirto: ");
    return scanner.nextLine();
  }

  protected abstract String toisenPelaajanSiirto(String ekanSiirto);

  public void pelaa() {
    Tuomari tuomari = new Tuomari();

    System.out.println("peli loppuu kun pelaaja antaa virheellisen siirron eli jonkun muun kuin k, p tai s");

    String ekanSiirto = ensimmaisenPelaajanSiirto();
    String tokanSiirto = toisenPelaajanSiirto(ekanSiirto);

    while (onkoOkSiirto(ekanSiirto) && onkoOkSiirto(tokanSiirto)) {
      tuomari.kirjaaSiirto(ekanSiirto, tokanSiirto);
      System.out.println(tuomari);
      System.out.println();

      ekanSiirto = ensimmaisenPelaajanSiirto();
      tokanSiirto = toisenPelaajanSiirto(ekanSiirto);
    }

    System.out.println();
    System.out.println("Kiitos!");
    System.out.println(tuomari);

  }

  protected static boolean onkoOkSiirto(String siirto) {
    return "k".equals(siirto) || "p".equals(siirto) || "s".equals(siirto);
  }
}
