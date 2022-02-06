# Programowanie-III  
Przełożenie projektu z Języków Skryptowych na język Java  

## Opis programu
### Jest to zadanie z konkursu algorytmion  
  Pasjans jest grą karcianą (najczęściej jednoosobową), której celem jest ułożenie krat wg pewnego wzorca. W zdecydowanej większości pasjansów powodzenie gracza zależy od jego umiejętności, istnieje jednak i taki, który zależy tylko od „szczęścia” gracza. W pasjansie tym, po
przetasowaniu talii 24 krat, układamy (koszulkami do góry) cztery rzędy krat po sześć – z
wyjątkiem rzędu czwartego, w którym ostatnią kartę zatrzymujemy w dłoni. Celem gry jest
ułożenie kart we właściwej kolejności: w pierwszym rzędzie kiery (9, 10, walet, dama, król i
as), w kolejnych odpowiednie kolory to: karo, trefl i pik (kolejność figur jak w kierach).
Gra kończy się, gdy w dłoni mieć będziemy asa pik. Jeśli kartą w dłoni nie jest as pik,
to kładziemy ją (obrazkiem do góry) na odpowiadające jej miejsce (np. gdyby była to dama
tref, to odłożylibyśmy ją do rzędu trzeciego do czwartej kolumny), biorąc w dłoń leżącą tam
kartę. Jeśli natrafimy ostatecznie na asa pik, to (o ile będzie taka potrzeba) odsłaniamy,
nie zmieniając ich położeń w układzie, pozostałe nieodsłonięte (leżące koszulkami do góry)
dotychczas karty. Jeżeli wszystkie karty leżeć będą we właściwej kolejności – wygraliśmy, jeśli
nie – przegraliśmy.
Napisz program, który generował będzie losowe rozłożenie kart, a następnie rozgrywał
będzie partie tego pasjansa. Program działał będzie do pierwszego zwycięstwa. Partie przegrane nie interesują nas, chcemy jedynie wiedzieć, za którym razem wygraliśmy oraz zobaczyć
wizualizację zwycięskiej partii. Przez wizualizację rozumiemy tutaj kolejne ruchy gracza począwszy od wejściowego układu, a skończywszy na asie pik „w dłoni” (łącznie z ewentualnym
odsłanianiem kart nieodsłoniętych). Sposób tej wizualizacji pozostawiamy w gestii rozwiązującego.    
  
Ponadto na potrzeby projektu, program przyjmuje plik wejściowy z idealną wygraną rozgrywką.

## Opis danych wejściowych
Danymi wejściowymi jest układ kart, ułożonych w taki sposób, aby program rozegrał od razu
wygraną partię.

## Opis danych wyjściowych
Danymi wyjściowymi są lista kroków wykonanych przy wygranej rozgrywce oraz liczba gier
rozegranych by osiągnąć zwycięstwo. Owe dane są potrzebne do wizualizacji.

## Start
Do uruchomienia programu mamy dwie możliwości. Można uruchomić program Pasjans.java,
który w sposób losowy ustawia rozłożenie kart w talii i działa dopóki nie trafi na wygraną.
Drugą opcją jest PlayFromInputDeck.java, który, jak wynika z nazwy, rozgrywa partię z
ustawioną z góry talią. Oba programy generują plik HTML o nazwie raport.html. Aby
zobaczyć wynik działania programu, należy uruchomić ten plik.
