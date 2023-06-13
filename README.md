
# Czat internetowy z podziałem na pokoje

Celem aplikacji jest dostarczenie użytkownikom czatu z podziałem na pokoje, który będzie dostępny z poziomu przeglądarki poprzez stworzone w tym celu API.




## Autorzy

- Adam Mężydło - amezydlo@student.agh.edu.pl
- Maciej Sieniek - sieniek@student.agh.edu.pl


## Opis projektu
Aplikacja umożliwia:

- tworzenie kont użytkowników
- dołączanie do istniejących pokoi przez zalogowanych użytkowników
- tworzenie nowych pokoi zabezpieczonych hasłem
- wysyłanie wiadomości w obrędbie danego pokoju

Do każdego pokoju może dołączyć ograniczona liczba użytkowników znających hasło. Jeśli pokój jest pełny, nowi użytkownicy nie będą w stanie do niego dołączyć.

Pokoje, w których nie ma użytkowników, zostają automatycznie usuwane.
## Stos technologiczny

**Backend:** Java, Spring Boot, PostgreSQL

**Frontend:** ReactJS (?)


## Struktura bazy danych

### Schemat
![App Screenshot](https://via.placeholder.com/468x300?text=App+Screenshot+Here)

### Tabele
**ROOMS**

Opis kolumn:

|Kolumna        |Opis                                                  |
|---------------|------------------------------------------------------|
|room_id **PK** |automatycznie wygenerowane **ID** pokoju              | 
|name           |nazwa pokoju                                          |
|capacity       |maksymalna liczba osób w pokoju                       |
|password       |prywatne hasło dające dostęp do pokoju                |
|users  **FK**  |klucz obcy do tabeli łączącej pokoje z użytkownikami  |
|messages **FK**|klucz obcy do tabeli łączącej pokoje z wiadomościami  |


DDL:

```sql
select * from Table
```

Przykładowe dane:

|room_id|name|capacity|password|users|messages|
|-------|----|--------|--------|-----|--------|
|-------|----|--------|--------|-----|--------|
|-------|----|--------|--------|-----|--------|
|-------|----|--------|--------|-----|--------|


**USERS**

Opis kolumn:

|Kolumna        |Opis                                                  |
|---------------|------------------------------------------------------|
|user_id **PK** |automatycznie wygenerowane **ID** użytkownika         | 
|username       |nazwa użytkownika (*nick*)                            |
|password       |hasło do konta użytkownika                            |
|rooms **FK**   |klucz obcy do tabeli łączącej użytkowników z pokojami |




DDL

```sql
select * from Table
```




Przykładowe dane:
|user_id|username|password|rooms|messages|
|-------|--------|--------|-----|--------|
|-------|--------|--------|-----|--------|
|-------|--------|--------|-----|--------|
|-------|--------|--------|-----|--------|



**MESSAGES**


Opis kolumn:

|Kolumna           |Opis                                                  |
|------------------|------------------------------------------------------|
|message_id **PK** |automatycznie wygenerowane **ID** wiadomości          | 
|content           |treść wiadomości                                      |
|sent_date         |data wysłania wiadomości                              |
|state             |stan wysłania (*ERROR*, *PENDING*, *SENT*, *RECEIVED*) |

DDL:

```sql
select * from Table
```

Przykładowe dane:

|message_id|content|sent_date|state|
|----------|-------|---------|-----|
|----------|-------|---------|-----|
|----------|-------|---------|-----|
|----------|-------|---------|-----|



