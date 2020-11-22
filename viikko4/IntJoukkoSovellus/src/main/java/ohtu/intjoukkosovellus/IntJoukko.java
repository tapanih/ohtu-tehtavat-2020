
package ohtu.intjoukkosovellus;

import java.security.InvalidParameterException;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;        // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;             // Joukon luvut säilytetään taulukon alkupäässä.
    private int alkioidenLkm = 0;    // Tyhjässä joukossa alkioiden_määrä on nolla.

    public IntJoukko() {
        this(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new InvalidParameterException("kapasiteetti ei voi olla negatiivinen");
        }
        if (kasvatuskoko < 0) {
            throw new InvalidParameterException("kasvatuskoko ei voi olla negatiivinen");
        }
        this.ljono = new int[kapasiteetti];
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (alkioidenLkm == ljono.length) {
                kasvataTaulukkoa();
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        return false;
    }

    private void siirraAlkioitaVasemmalle(int alkuIndeksi) {
        for (int i = alkuIndeksi; i < alkioidenLkm - 1; i++) {
            ljono[i] = ljono[i + 1];
        }
    }

    public boolean poista(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                siirraAlkioitaVasemmalle(i);
                alkioidenLkm--;
                return true;
            }
        }
        return false;
    }

    private void kasvataTaulukkoa() {
        int[] uusi = new int[alkioidenLkm + kasvatuskoko];
        System.arraycopy(ljono, 0, uusi, 0, ljono.length);
        ljono = uusi;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        }

        String tuotos = "{";
        for (int i = 0; i < alkioidenLkm - 1; i++) {
            tuotos += ljono[i];
            tuotos += ", ";
        }
        tuotos += ljono[alkioidenLkm - 1];
        tuotos += "}";
        return tuotos;
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        System.arraycopy(ljono, 0, taulu, 0, taulu.length);
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdiste = new IntJoukko();
        for (int luku : a.toIntArray()) {
            yhdiste.lisaa(luku);
        }
        for (int luku : b.toIntArray()) {
            yhdiste.lisaa(luku);
        }
        return yhdiste;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkaus = new IntJoukko();
        for (int luku : a.toIntArray()) {
            if (b.kuuluu(luku)) {
                leikkaus.lisaa(luku);
            }
        }
        return leikkaus;
    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko erotus = new IntJoukko();
        for (int luku : a.toIntArray()) {
            if (!b.kuuluu(luku)) {
                erotus.lisaa(luku);
            }
        }
        return erotus;
    }
}
