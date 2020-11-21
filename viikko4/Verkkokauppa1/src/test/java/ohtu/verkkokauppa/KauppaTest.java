package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {

    Pankki pankki;
    Viitegeneraattori viite;
    Varasto varasto;
    Kauppa k;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viite = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        k = new Kauppa(varasto, pankki, viite);

        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(),anyInt());
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodinTilisiirtoParametritOvatOikein() {

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(5));
    }

    @Test
    public void useammanTuotteenOstoksenPaatyttyaPankinMetodinTilisiirtoParametritOvatOikein() {
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on piimä jonka hinta on 6 ja saldo 10
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 6));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(11));
    }

    @Test
    public void kahdenSamanTuotteenOstoksenPaatyttyaPankinMetodinTilisiirtoParametritOvatOikein() {
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on piimä jonka hinta on 6 ja saldo 10
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 6));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(2);     // ostetaan kaksi kappaletta tuotetta numero 2 eli piimää
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(12));
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodinTilisiirtoParametritOvatOikeinVaikkaToinenTuoteOnLoppu() {

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 1
        when(varasto.saldo(1)).thenReturn(1);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // piimä on loppu
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 6));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);     // koitetaan ostaa piimää, vaikka se on loppu
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(5));
    }

    @Test
    public void edellisenOstoksenHintaEiVaikutaUudenOstoksenHintaan() {

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(5));

        // tehdään toiset ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("matti", "54321");

        // varmistetaan, että edellisen ostoksen hinta ei vaikuta uuteen ostokseen
        verify(pankki).tilisiirto(eq("matti"), anyInt(), eq("54321"), anyString(), eq(5));
    }

    @Test
    public void jokaiselleOstokselleLuodaanOmaViiteNumero() {
        when(viite.uusi())
                .thenReturn(42)
                .thenReturn(43)
                .thenReturn(44);


        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikealla viitenumerolla
        verify(pankki).tilisiirto(anyString(), eq(42), anyString(), anyString(), anyInt());

        // tehdään toiset ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("matti", "5432§");

        // varmistetaan, että toisessa ostoksessa käytetään toista viitenumeroa
        verify(pankki).tilisiirto(anyString(), eq(43), anyString(), anyString(), anyInt());
    }

    @Test
    public void tuotteenPoistaminenOstoskoristaVaikuttaaHOstostenHintaan() {

        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 10
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        // määritellään että tuote numero 2 on piimä jonka hinta on 6 ja saldo 10
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piimä", 6));

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan kaksi kappaletta tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);
        k.poistaKorista(1);   // poistetaan toinen
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(6));
    }
}