import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

class GeneratorStrony {
    String[] kartyKolor = {"KIER", "KARO", "TREFL", "PIK"};
    String[] kartyFigura = {"9", "10", "J", "Q", "K", "A"};

    private Hashtable<String, int[]> generujMapePozycji() {
        var mapaPozycji = new Hashtable<String, int[]>();

        for (int i = 0; i < kartyFigura.length; i++) {
            for (int j = 0; j < kartyKolor.length; j++) {
                int[] pozycja = {i, j};
                mapaPozycji.put(kartyFigura[i] + " " + kartyKolor[j], pozycja);
            }
        }
        return mapaPozycji;
    }

    private ArrayList<ArrayList<String>> wygenerujPustaMape() {
        var mapa = new ArrayList<ArrayList<String>>();
        for (var figura : kartyFigura) {
            var wiersz = new ArrayList<String>();
            for (var kolor: kartyKolor) {
                wiersz.add("X");
            }
            mapa.add(wiersz);
        }
        return mapa;
    }

    private void odkryjCalaMape(ArrayList<ArrayList<String>> mapa, Hashtable<String, int[]> mapaPozycji) {
        Enumeration<String> enumeration = mapaPozycji.keys();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            int[] pozycja = mapaPozycji.get(key);
            var wiersz = mapa.get(pozycja[0]);
            wiersz.set(pozycja[1], key);
        }
    }

    private String wykonajKrokNaMapie(
        ArrayList<ArrayList<String>> mapa,
        Hashtable<String, int[]> mapaPozycji,
        String krok
    ) {
        String[] kartyKroku = krok.split(" -> ");
        String trzymana = kartyKroku[0];
        String otrzymana = kartyKroku[1];
        var pozycja = mapaPozycji.get(trzymana);
        var wiersz = mapa.get(pozycja[0]);
        wiersz.set(pozycja[1], trzymana);
        return otrzymana;
    }

    private String mapaNaTabeleHTML(ArrayList<ArrayList<String>> mapa, String reka) {
        String html = "<table>\n";
        html += "<tr>\n";
        html += "<td>Kier</td><td>Karo</td><td>Trelf</td><td>Pik</td>\n";
        html += "</tr>\n";

        for (var wiersz: mapa) {
            html += "<tr>\n";
            for (var kolumna: wiersz) {
                html += "<td>" + kolumna + "</td>";
            }
            html += "</tr>\n";
        }

        html += "<tr><td colspan='4'>Trzymana Karta: " + reka + "</td></tr>\n";

        html += "</table>";
  
        return html;
    }

    private String ramyHTMLGora() {
        String html = "";
        html += "<!DOCTYPE html>\n";
        html += "<html lang=\"pl\">\n";
        html += "  <head>\n";
        html += "    <meta charset=\"utf-8\" />\n";
        html += "    <title>Raport - Wizualizacja</title>\n";
        html += "    <style>td { text-align: center; width: 300px; } table, tr, td { border: solid 1px black } }</style>\n";
        html += "  </head>\n";
        html += "  <body><center>\n";
        return html;
    }

    private String ramyHTMLDol() {
        String html = "</center></body>\n";
        html += "</html>\n";

        return html;
    }

    private String generujHTMLa(ArrayList<String> listaKrokow, String czyWygrana, String liczbaGier) {
        String html = ramyHTMLGora();

        html += "<h1>" + czyWygrana + " po " + liczbaGier + " grach" + "</h1>";
        html += "<table>\n";
        html += " <tr>\n";
        html += "   <td>Krok</td> <td>Plansza</td>";
        html += " </tr>\n";

        int i = 0;
        var mapaPozycji = generujMapePozycji();
        var mapa = wygenerujPustaMape();

        // Krok 0

        var kartaWReku = listaKrokow.get(0).split(" -> ")[0];
        html += "<tr>\n";
        html += "<td>0.</td>";
        html += "<td>" + mapaNaTabeleHTML(mapa, kartaWReku) + "</td>";
        html += "</tr>";

        for (var krok : listaKrokow) {
            i++;
            kartaWReku = wykonajKrokNaMapie(mapa, mapaPozycji, krok);
            html += "<tr>\n";
            html += "<td>" + String.valueOf(i) + ".</td>";
            html += "<td>" + mapaNaTabeleHTML(mapa, kartaWReku) + "</td>";
            html += "</tr>";
        }

        // Koniec
        odkryjCalaMape(mapa, mapaPozycji);
        html += "<tr>\n";
        html += "<td>" + "KONIEC" + "</td>";
        html += "<td>" + mapaNaTabeleHTML(mapa, kartaWReku) + "</td>";
        html += "</tr>";
      
        html += "</table>\n";
        html += ramyHTMLDol();
        return html;
    }

    public static void main(String[] args) {
        try {
            ArrayList<String> listaKrokow = new ArrayList<String>();
            String czyWygrana = "";
            String liczbaGier = "";

            File listaKrokowFile = new File("listaKrokow.txt");
            Scanner listaKrokowReader = new Scanner(listaKrokowFile);

            while (listaKrokowReader.hasNextLine()) {
                listaKrokow.add(listaKrokowReader.nextLine());
            }

            listaKrokowReader.close();
            
            File rezultatFile = new File("rezultat.txt");
            Scanner rezultatReader = new Scanner(rezultatFile);

            if (rezultatReader.hasNextLine()) {
                String rezultat = rezultatReader.nextLine();
                String[] rezParted = rezultat.split(" ");
                czyWygrana = rezParted[0];
                liczbaGier = rezParted[1];
            }

            rezultatReader.close();

            var generator = new GeneratorStrony();
            String html = generator.generujHTMLa(listaKrokow, czyWygrana, liczbaGier);

            Path outputPath = Paths.get("raport.html");
            Files.write(outputPath, Arrays.asList(html.split("\n")), StandardCharsets.UTF_8); 
        }
        catch(Exception ex) {
            System.out.println("Error pliku!");
            ex.printStackTrace();
        }

    }
}