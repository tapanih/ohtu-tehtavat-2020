package laskin;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public abstract class Komento {
  protected final TextField tuloskentta;
  protected final TextField syotekentta;
  protected final Button nollaa;
  protected final Button undo;
  protected final Sovelluslogiikka sovellus;

  public Komento(TextField tuloskentta, TextField syotekentta, Button nollaa, Button undo, Sovelluslogiikka sovellus) {
    this.tuloskentta = tuloskentta;
    this.syotekentta = syotekentta;
    this.nollaa = nollaa;
    this.undo = undo;
    this.sovellus = sovellus;
  }

  protected void paivita() {
    syotekentta.setText("");
    tuloskentta.setText(String.valueOf(sovellus.tulos()));
    nollaa.setDisable(sovellus.tulos() == 0);
    undo.setDisable(false);
  }

  public abstract void suorita();

  public void peru() {
    sovellus.undo();
    tuloskentta.setText(String.valueOf(sovellus.tulos()));
    undo.setDisable(true);
  };
}
