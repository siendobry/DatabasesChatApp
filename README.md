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
CREATE TABLE public.rooms (
    room_id integer NOT NULL,
    capacity integer NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255)
);


ALTER TABLE public.rooms OWNER TO postgres;

ALTER TABLE ONLY public.rooms ALTER COLUMN room_id SET DEFAULT nextval('public.rooms_room_id_seq'::regclass);

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (room_id);


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
CREATE TABLE public.users (
    user_id integer NOT NULL,
    password character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


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
|state             |stan wysłania (*ERROR*, *PENDING*, *SENT*, *RECEIVED*)|

DDL:

```sql
CREATE TABLE public.messages (
    message_id integer NOT NULL,
    content character varying(255) NOT NULL,
    sender character varying(255) NOT NULL,
    sent_date timestamp(6) without time zone NOT NULL
);


ALTER TABLE public.messages OWNER TO postgres;

ALTER TABLE ONLY public.messages ALTER COLUMN message_id SET DEFAULT nextval('public.messages_message_id_seq'::regclass);

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (message_id);



```

Przykładowe dane:

|message_id|content|sent_date|state|
|----------|-------|---------|-----|
|----------|-------|---------|-----|
|----------|-------|---------|-----|
|----------|-------|---------|-----|



**ROOMS_MESSAGES**

Opis kolumn:

|Kolumna                    |Opis                                                  |
|---------------------------|------------------------------------------------------|
|room_room_id **PK**        |wraz z messages_message_id tworzy klucz główny złożony| 
|messages_message_id **PK** |wraz z room_room_id tworzy klucz główny złożony       |



DDL

```sql
CREATE TABLE public.rooms_messages (
    room_room_id integer NOT NULL,
    messages_message_id integer NOT NULL
);


ALTER TABLE public.rooms_messages OWNER TO postgres;


ALTER TABLE ONLY public.rooms_messages
    ADD CONSTRAINT rooms_messages_pkey PRIMARY KEY (room_room_id, messages_message_id);


ALTER TABLE ONLY public.rooms_messages
    ADD CONSTRAINT uk_bia0hyy52d771gr2c33uy7qc UNIQUE (messages_message_id);

ALTER TABLE ONLY public.rooms_messages
    ADD CONSTRAINT fk2dji8l9p308w6un0658yqesrf FOREIGN KEY (room_room_id) 
        REFERENCES public.rooms(room_id);


ALTER TABLE ONLY public.rooms_messages
    ADD CONSTRAINT fk5gommedfr14nu4y74hgilsowd FOREIGN KEY (messages_message_id) 
        REFERENCES public.messages(message_id);

```




Przykładowe dane:
|user_id|username|password|rooms|messages|
|-------|--------|--------|-----|--------|
|-------|--------|--------|-----|--------|
|-------|--------|--------|-----|--------|
|-------|--------|--------|-----|--------|




**ROOMS_USERS**

Opis kolumn:

|Kolumna               |Opis                                                  |
|----------------------|------------------------------------------------------|
|rooms_room_id **PK**  |wraz z rooms_room_id tworzy klucz główny złożony      | 
|users_user_id **PK**  |wraz z users_user_id tworzy klucz główny złożony      |



DDL

```sql
CREATE TABLE public.rooms_users (
    rooms_room_id integer NOT NULL,
    users_user_id integer NOT NULL
);


ALTER TABLE public.rooms_users OWNER TO postgres;


ALTER TABLE ONLY public.rooms_users
    ADD CONSTRAINT rooms_users_pkey PRIMARY KEY (rooms_room_id, users_user_id);


ALTER TABLE ONLY public.rooms_users
    ADD CONSTRAINT fkj74qd3fh8t91h2rqugym2aukr FOREIGN KEY (rooms_room_id) 
        REFERENCES public.rooms(room_id);


ALTER TABLE ONLY public.rooms_users
    ADD CONSTRAINT fkonfvsoo49ldsby39eibxeg7a3 FOREIGN KEY (users_user_id) 
        REFERENCES public.users(user_id);
```




Przykładowe dane:
|user_id|username|password|rooms|messages|
|-------|--------|--------|-----|--------|
|-------|--------|--------|-----|--------|
|-------|--------|--------|-----|--------|
|-------|--------|--------|-----|--------|

## Serwer

Konfiguracja serwera Tomcat oraz połączenia z bazą danych PostgreSQL

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/chatapp
    username: chatapp
    password: chatapp
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect

server:
  port: 10001
```

Aplikacja będzie się łączyła z bazą danych o nazwie `chatapp`, jako użytkownik o nazwie i haśle `chatapp`, zatem użytkownik musi posiadać odpowiednie konto i mieć stworzoną bazę danych o podanej nazwie.

W celach testowych zmieniony został domyślny port serwera `Tomcat`. Można go przywrócić zmieniając wpis na:
```yml
server:
  port: 8080
```

lub całkowicie usunąć wpis dotyczący serwera.

Należy zauważyć, że przy takiej konfiguracji, za każdym razem podczas wyłączania serwera, na schemacie bazy danych zostanie wykonany DROP, a zatem dane zostaną utracone.

W wersji produkcyjnej należałoby ustawić parametr `ddl-auto` na `none`.
