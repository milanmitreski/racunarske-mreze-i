## Pogodi broj (TCP)
Napraviti TCP klijent-server aplikaciju koja simulira igru ”Pogodi broj”. Na serveru se generiše ceo broj od 1 do 100, a zadatak klijenta je da taj broj pogodi,

- Implementirati klijentsku TCP aplikaciju koristeći Java Socket API. Klijent se povezuje na server na portu 12321, prima poruku ”Pogodi koji broj od 1 do 100 sam zamislio”, i šalje broj koji je odabrao. Nakon toga, prima poruke u formatu ”Zamišljeni broj je manji/veći od toga”. Klijent šalje novi broj, sve dok se broj ne pogodi, nakon čega dobija poruku ”Čestitam! Pogodili ste broj.”
- Implementirati serversku TCP aplikaciju koristeći Java Socket API. Uloga servera je da osluškuje na portu 12321, prihvata klijente i pokreće zasebnu nit za svakog klijenta. 
- Pojedinačne niti koje obradjuju klijente imaju ulogu onog koji zamišlja broj. Potrebno je generisati jedan ceo broj od 1 do 100 i obavestiti klijenta da je igra počela porukom: ”Pogodi koji broj od 1 do 100 sam zamislio”. Nakon toga, prima broj koji je klijent poslao, proverava da li je veći ili manji od zamišljenog broja, i šalje odgovarajuću poruku. Igra se završava kada klijent pogodi broj, nakon čega dobija poruku da je broj pogodjen. 
- Postarati se da su svi resursi ispravno zatvoreni u slučaju naglog isključivanja klijenta ili drugih izuzetaka.

Primer rada:
```
server : Pogodi koji broj od 1 do 100 sam zamislio
klijent: 50
server : Zamisljeni broj je veci od toga
klijent: 75
server : Zamisljeni broj je veci od toga
klijent: 87
server : Zamisljeni broj je manji od toga
klijent: 81
server : Zamisljeni broj je manji od toga
klijent: 78
server : Cestitam! Pogodili ste broj!
```