TRUNCATE rateyourbooks.books;

INSERT INTO rateyourbooks.books (id, title, author)
VALUES (1, 'Fourth Wing', 'Rebecca Yarros');
INSERT INTO rateyourbooks.books (id, title, author)
VALUES (2, 'Im Grunde Gut', 'Rutger Bregman');
INSERT INTO rateyourbooks.books (id, title, author)
VALUES (3, 'Utopien für Realisten', 'Rutger Bregman');
INSERT INTO rateyourbooks.books (id, title, author)
VALUES (4, 'Eine kurze Geschichte der Menschheit', 'Yuval Noah Harari');
INSERT INTO rateyourbooks.books (id, title, author)
VALUES (5, 'Baustellen der Nation', 'Philip Banse');

ALTER TABLE books
    ALTER COLUMN id RESTART WITH 6;
