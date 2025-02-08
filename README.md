Java Könyvkatalógus program

szükséges adatbázis:

CREATE TABLE books (
id INT(11) AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(250) COLLATE utf8_hungarian_ci NOT NULL,
publication_year INT(11) NOT NULL,
price DOUBLE NOT NULL,
authors VARCHAR(100) COLLATE utf8_hungarian_ci NOT NULL
);

CREATE TABLE users (
id INT(11) AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) COLLATE utf8_hungarian_ci NOT NULL UNIQUE,
password VARCHAR(255) COLLATE utf8_hungarian_ci NOT NULL,
role ENUM('ADMIN', 'USER', 'GUEST') COLLATE utf8_hungarian_ci NOT NULL
);

a program elinditásakor beirjuk a megfelelő felhasználónevet és jelszavat,
utánna pedig szabadon használhatjuk az alábbi funkciókat:
1.Könyv hozzáadása
2.Könyv törlése
3.Könyvek listázása
4.Könyv keresése
5.Mentés fájlba
6.Betöltés fájlból
7.Mentés adatbázisba
8.Kilépés