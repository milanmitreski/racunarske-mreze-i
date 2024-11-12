# Računarkse mreže (I), 5. vežbe

## Adresiranje

Prilikom izgradnje jedne računarske mreže, potrebno je svakom čvoru u toj mreži dodeliti adresu, koja služi za jedinstvenu identifikaciju tog čvora u mreži. Dodeljivanje adresa u okviru računarske mreže naziva se **adresiranje**. Oblik adresa koji se danas koristi u računarskim mrežama jesu **IP (Internet Protocol) adrese**.

Razvijanjem IP-a, razvijale su se i verzije IP adresa. Danas su u upotrebi dve verzije:

1. IPv4 adrese, veličine 4 bajta, tj. 32 bita.
2. IPv6 adrese, veličine 16 bajtova, tj. 128 bita.

### IPv4 adrese

- dužine 32 bit-a, tj. 4 byte-a
- ima ih $2^{32}=4\,\,294\,\,967\,\,296$ (broj mogućih adresa)
- zapis: _dotted quad_ - 4 broja iz opsega $[0,255]$ odvojena tačkom (jedan bajt predstavlja broj iz pomenutog opsega)
- primer: `147.95.15.25`, `192.168.0.7`

Ključno pitanje je kako se adrese dodeljuju čvorovima i kako se ovakav skup adresa deli na (manje) mreže. **IANA (Internet Assigned Numbers Authority)** je organizacija koja globalno administrira podelu IP adresa.

### Naivni pristup

IP adresu možemo podeliti na dva dela:

- prefiks, dužine $1\mathrm B$ koji identifikuje mrežu u kojoj se čvor sa tom IP adresom nalazi
- ostatak IP adrese, dužine $3\mathrm B$ koji identifikuje adresu tog čvora u datoj mreži

Ovakvim pristupom, podelili smo sve dostupne IP adrese u $256$ mreža, pri čemu je svaka mreža veličine $2^{24}=16\,\,777\,\,216$ (veličina mreže jeste ukupan broj adresa koji pripadaju toj mreži).

Ovakva podela je izuzetno nefleksibilna i nepraktična iz više razloga. Prvi je taj što smo dobili izuzetno mali broj mogućih mreža. Drugi razlog je taj što je svaka mreža ogromna, i u slučaju da nam je potrebna mreža veličine $2000$, morali bi da "zakupimo" mrežu veličine oko $16\,\,000\,\,000$.

### Classful adresiranje

Ovde takođe zadržavamo ideju podele IP adrese na dva dela:

- adresa mreže (levi deo adrese)
- adresa hosta u mreži (desni deo adrese)

U naivnom pristupu, fiksirali smo veličinu levog i desnog dela adrese. Ideja kod classful adresiranja jeste podela IP adresa u više klasa, pri čemu će veličina levog odnosno desnog dela adrese biti različita u zavisnosti od klase kojoj IP adresa pripada.

Podela IP adresa na klase u classful adresiranju:

| Klasa | Raspon (dotted quad)               | Raspon (binarno)                                                                            |
|-------|------------------------------------|---------------------------------------------------------------------------------------------|
| A     | `0.0.0.0` <br/>`127.255.255.255`   | `0\|000 0000 0000 0000 0000 0000 0000 0000`<br/>`0\|111 1111 1111 1111 1111 1111 1111 1111` |
| B     | `128.0.0.0` <br/>`191.255.255.255` | `10\|00 0000 0000 0000 0000 0000 0000 0000`<br/>`10\|11 1111 1111 1111 1111 1111 1111 1111` |
| C     | `192.0.0.0` <br/>`223.255.255.255` | `110\|0 0000 0000 0000 0000 0000 0000 0000`<br/>`110\|1 1111 1111 1111 1111 1111 1111 1111` |
| D     | `224.0.0.0` <br/>`239.255.255.255` | `1110\|0000 0000 0000 0000 0000 0000 0000`<br/>`1110\|1111 1111 1111 1111 1111 1111 1111`   |
| E     | `240.0.0.0` <br/>`255.255.255.255` | `1111\|0000 0000 0000 0000 0000 0000 0000`<br/>`1111\|1111 1111 1111 1111 1111 1111 1111`   |

Kako odrediti kojoj klasi pripada data IP adresa? Primetimo da svaka klasa ima prefiks kojim se karakterišu adrese te klase (deo adresa (u binarnom zapisu) odvojen uspravnom crtom). Prefiksi su: `0 (A), 10 (B), 110 (C), 1110 (D), 1111 (E)`. 

Kao što je i rečeno, podela adresa na levi i desni deo zavisi od toga kojoj klasi ta adresa pripada. Drugim rečima, svaku klasu delimo na različit broj mreža:

- Za adrese iz klase A, levi deo adrese je veličine $1\rm B$, dok je desni deo adrese veličine $3\rm B$. Ukupan broj mreža u klasi A je $2^7=128$ (prvi bit je fiksiran na `0`), dok je veličina svake mreže $2^{24}=16\,\,777\,\,216$
- Za adrese iz klase B,  levi deo adrese je veličine $2\rm B$, dok je desni deo adrese veličine $2\rm B$. Ukupan broj mreža u klasi A je $2^{14}=16\,\,384$ (prva dva bita su fiksirana na `10`), dok je veličina svake mreže $2^{16}=65\,\,536$
- Za adrese iz klase C,  levi deo adrese je veličine $3\rm B$, dok je desni deo adrese veličine $1\rm B$. Ukupan broj mreža u klasi A je $2^{21}=2\,\,097\,\,152$ (prva tri bita su fiksirana na `110`), dok je veličina svake mreže $2^{8}=256$

Adrese iz klasa D koriste se kao multicast adrese, dok se adrese iz klasa E koriste u eksperimentalne svrhe. Adrese iz klase A, B i C koriste se kao unicast adrese, odnosno adrese koje se dodeljuju korisnicima. 

Međutim, pri adresiranju postoje dodatne restrikcije:

- Prva adresa u mreži je rezervisana i ne može se dodeliti hostu. Ova adresa naziva se **adresa mreže**
- Poslednja adresa u mreži je rezervisana i ne može se dodeliti hostu. Ova adresa naziva se **broadcast adresa**. (služi za dostavljanje paketa svim čvorovima/hostovima u mreži)
- Mreže `0.0.0.0-0.255.255.255` i `127.0.0.0-127.255.255.255` (iz klase A) su rezervisane i koriste se (redom) za default rute odnosno loopback adrese
- Privatne adrese: određeni broj mreža iz klasa A, B i C rezervisan je za upotrebu u privatnim mrežama. To podrazumeva da se pomenute adrese neće propagirati na Internet, odnosno da te adrese ne predstavljaju adrese čvorova na Internetu. Takve adrese koriste se u lokalnim mrežama.
    - 1 mreža iz klase A: `10.0.0.0` - `10.255.255.255`
    - 16 mreža iz klase B: `172.16.0.0` - `172.16.255.255`, ... `172.31.0.0` - `172.31.255.255`
    - 256 mreža iz klase C: `192.168.0.0` - `192.168.0.255`, ... `192.168.255.0` - `192.168.255.255`

Iako su classful adresiranjem uvedene mreže različite veličine, pri čemu je broj mreža značajno povećan, i dalje se javlja problem nefleksibilnosti - šta ukoliko nam je potrebna mreža veličine $5000$?

### Classless adresiranje

Classless adresiranje omogućava da formiramo mreže veličine $2^n$ tj. možemo proizvoljan skup IP adresa sa zajedničkim levi delom adrese proizvoljne veličine posmatrati kao jednu mrežu. Međutim, javlja se pitanje - kako odrediti koji deo adrese je levi deo, a koji je desni deo - npr. kod classful adresiranja smo to određivali na osnovu klase kojoj adresa pripada.

Rešenje je dodavanje novog podatka uz samu vrednost IP adrese koja se naziva **maska**. Maska IP adrese sadrži informaciju o tome koji bitovi same adrese predstavljaju levi deo adrese, a koji bitovi predstavljaju desni deo adrese.

Posmatrajmo IP adresu `147.96.10.50/22`. Maska (zapisana u CIDR (Classless Inter-Domain Routing) formatu) je `/22` i daje informaciju da se levi deo adrese sastoji od prvih 22 bita. Alternativni zapis maske jeste binarni broj koji sadrži 22 jedinice i 10 nula (gledajući s leva na desno). Ovakav broj služi kao binarna maska (zbog čega je i ovaj podatak dobio naziv maska) koja služi za izdvajanje adrese mreže iz IP adrese koristeći bitovsku konjunkciju. Zapišimo adresu i masku u binarnom formatu:

```
1001 0011.0110 0000.0000 1010.0011 0010 - IP adresa
1111 1111.1111 1111.1111 1100.0000 0000 - maska

1001 0011.0110 0000.0000 10|00.0000 0000 - adresa mreže
1001 0011.0110 0000.0000 10|11.1111 1111 - broadcast adresa
```

Dakle, na ovaj način možemo dati skup IP adresa podeliti u mreže različitih veličina. Restrikcije koje smo definisali kod classful adresiranja prenose se i na classless adresiranje.