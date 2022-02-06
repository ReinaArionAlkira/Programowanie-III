import java.util.*;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.IOException;

class Pasjans {

  public Hashtable<String, String> mapa;
  public ArrayList<String> talia;
  public ArrayList<String> listaKrokow;

  public Pasjans() {
    mapa = new Hashtable<String, String>();
    talia = new ArrayList<String>();
    listaKrokow = new ArrayList<String>();
  }

  // funkcja rozpoczynająca grę
  // tworzenie talii, losowanie pozycji
  public void WygenerujGre() {
    // listy pomocnicze do tworzenia pełnej karty
    ArrayList<String> kartyKolor = new ArrayList<String>();
    kartyKolor.addAll(Arrays.asList("KIER", "KARO", "TREFL", "PIK"));
    ArrayList<String> kartyFigura = new ArrayList<String>();
    kartyFigura.addAll(Arrays.asList("9", "10", "J", "Q", "K", "A"));

    // uzupełnianie tablicy talia kartami
    for (String x : kartyKolor) {
      for (String y : kartyFigura) {
        this.talia.add(y + " " + x);
      }
    }
    // tasowanie talii
    Collections.shuffle(this.talia, new Random());
    // przypisywanie pozycji pól według kolejności i wstawianie tam wylosowanych
    // kart
    int i = 0;
    for (String x : kartyKolor) {
      for (String y : kartyFigura) {
        if (i == this.talia.size() - 1) {
          this.mapa.put(y + " " + x, "[]");
          break;
        }
        ;
        this.mapa.put(y + " " + x, talia.get(i));
        i += 1;
      }
    }
  }

  // funkcja zmieniająca karte na mapie
  public String zamienKarty(String karta) {
    String kartaNaMapie = this.mapa.get(karta);
    this.mapa.replace(karta, karta);
    return kartaNaMapie;
  }

  // zamiana kart z dłoni na pole dopóki trzymaną kartą nie będzie As Pik
  public ArrayList<String> rotacjaKart() {
    String trzymanaKarta = this.talia.get(this.talia.size() - 1);
    while (trzymanaKarta.compareTo("A PIK") != 0) {
      String krok = trzymanaKarta + " -> ";
      trzymanaKarta = zamienKarty(trzymanaKarta);
      krok += trzymanaKarta;
      this.listaKrokow.add(krok);
    }
    this.listaKrokow.add("A PIK -> []");
    zamienKarty(trzymanaKarta);
    return this.listaKrokow;
  }

  public boolean sprawdzWynik() {
    boolean wygrana = true;
    Set<String> setOfKeys = this.mapa.keySet();
    for (String x : setOfKeys) {
      if (!this.mapa.get(x).equals(x)) {
        wygrana = false;
        break;
      }
    }
    return wygrana;
  }

  public String naWyjscie() {
    String wynik = "";
    Set<String> setOfKeys = this.mapa.keySet();
    for (String x : setOfKeys) {
      wynik += x + " => " + this.mapa.get(x) + "\n";
    }
    return wynik;
  }

  public static void main(String[] args) {
    Pasjans gra = new Pasjans();

    ArrayList<String> dozwoloneKolory = new ArrayList<String>();
    dozwoloneKolory.addAll(Arrays.asList("KIER", "KARO", "TREFL", "PIK"));
    ArrayList<String> dozwoloneFigury = new ArrayList<String>();
    dozwoloneFigury.addAll(Arrays.asList("9", "10", "J", "Q", "K", "A"));
    
    try{
      File taliaInput = new File("wejscie.txt");
      Scanner taliaInputReader = new Scanner(taliaInput);

            while (taliaInputReader.hasNextLine()) {
                gra.talia.add(taliaInputReader.nextLine());
            }

            taliaInputReader.close();
    }
    catch(Exception ex) {
      System.out.println("Error pliku!");
      ex.printStackTrace();
    }

    gra.WygenerujGre();
    gra.rotacjaKart();

    try {
      FileWriter myWriter = new FileWriter("listaKrokow.txt");
      myWriter.write(String.join("\n", gra.listaKrokow));
      myWriter.close();
      System.out.println("Lista kroków została zapisana w pliku listaKrokow.txt.");
    } catch (IOException e) {
      System.out.println("Napotkano problemy...");
      e.printStackTrace();
    }
    try {
      FileWriter myWriter = new FileWriter("rezultat.txt");
      myWriter.write("WYGRANA 1");
      myWriter.close();
      GeneratorStrony.main(args);
      System.out.println("Uruchom wygenerowaną wizualizację w postaci pliku HTML");
    } catch (IOException e) {
      System.out.println("Napotkano problemy...");
      e.printStackTrace();
    }
  }
}