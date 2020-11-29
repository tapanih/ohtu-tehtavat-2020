package laskin;

public class Sovelluslogiikka {
 
    private int tulos;
    private int edellinen;
 
    public void plus(int luku) {
        edellinen = tulos;
        tulos += luku;
    }
     
    public void miinus(int luku) {
        edellinen = tulos;
        tulos -= luku;
    }
 
    public void nollaa() {
        edellinen = tulos;
        tulos = 0;
    }
 
    public int tulos() {
        return tulos;
    }

    public void undo() {
        tulos = edellinen;
    }
}