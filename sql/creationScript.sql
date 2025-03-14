-- Database: IT_library

-- DROP DATABASE IF EXISTS "IT_library";
DROP DATABASE IT_Library
IF NOT EXISTS (SELECT *
				FROM sys.databases
				WHERE name = 'IT_Library')
	CREATE DATABASE IT_Library
	
USE IT_Library


	CREATE TABLE topics(
		topic_id INT IDENTITY(1,1) PRIMARY KEY,
		topic_name VARCHAR(225) UNIQUE NOT NULL,
		topic_deleted BIT DEFAULT 0,
		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE()
	);
	--ALTER TABLE Topics
	--ADD UNIQUE (topic_name)
	CREATE TABLE prog_langs(
		prog_lang_id INT IDENTITY(1,1) PRIMARY KEY,
		prog_lang_name VARCHAR(225) UNIQUE NOT NULL,
		prog_lang_deleted BIT DEFAULT 0,
		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE()
	);
	--ALTER TABLE prog_langs
	--ADD UNIQUE (prog_lang_name)
	CREATE TABLE authors(
		author_id INT IDENTITY(1,1) PRIMARY KEY,
		author_name VARCHAR(255) UNIQUE NOT NULL,
		author_link VARCHAR(255),
		author_deleted BIT DEFAULT 0,
		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE()
	); 
	CREATE TABLE users(
		user_id INT IDENTITY(1,1) PRIMARY KEY,
		user_name VARCHAR(255) UNIQUE NOT NULL,
		user_password VARCHAR(255) NOT NULL,
		user_email VARCHAR(255),
		user_phone VARCHAR(255),
		user_is_admin BIT DEFAULT 0,
		user_deleted BIT DEFAULT 0,
		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE()
	)
	CREATE TABLE logs(
		log_id INT IDENTITY(1,1) PRIMARY KEY,
		user_id integer NOT NULL,
		log_date DATETIME DEFAULT GETDATE(),
		CONSTRAINT FK_logs_users FOREIGN KEY (user_id) REFERENCES users(user_id)
	);
	CREATE TABLE books(
		book_id INT IDENTITY(1,1) PRIMARY KEY,
		author_id INTEGER NOT NULL,
		book_name varchar(255) NOT NULL,
		book_publish_date DATE,
		book_description VARCHAR(255),
		book_language VARCHAR(100),
		book_file_url varchar(255) UNIQUE NOT NULL,
		book_edition INTEGER,
		book_nbr_downloads INTEGER DEFAULT 0,
		book_status VARCHAR(100),
		book_deleted BIT DEFAULT 0,
		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE(),
		CONSTRAINT CK_status CHECK (book_status IN ('accepted','pending','rejected')),
		CONSTRAINT CK_edition CHECK (book_edition > 0 AND book_edition < 2090),
		CONSTRAINT CK_language CHECK (book_language IN ('french','english','arab')),
		CONSTRAINT FK_books_authors FOREIGN KEY (author_id) REFERENCES authors(author_id)
	);
	CREATE TABLE comments(
		comment_id INT IDENTITY(1,1) PRIMARY KEY,
		user_id INTEGER NOT NULL,
		book_id INTEGER NOT NULL,
		comment_text NVARCHAR(max) NOT NULL,
		comment_deleted BIT DEFAULT 0,
		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE(),
		CONSTRAINT CK_comment_text CHECK (LEN(comment_text) > 3),
		CONSTRAINT FK_comments_books FOREIGN KEY (book_id) REFERENCES books(book_id),
		CONSTRAINT FK_comments_users FOREIGN KEY (user_id) REFERENCES users(user_id)
	);
	CREATE TABLE ratings(
		user_id INTEGER NOT NULL,
		book_id INTEGER NOT NULL,
		rating_score INTEGER NOT NULL,
		rating_deleted BIT DEFAULT 0,
		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE(),
		CONSTRAINT PK_rating_user_book PRIMARY KEY (user_id,book_id),
		CONSTRAINT CK_rating_score CHECK ( rating_score >= 0 AND rating_score <= 5),
		CONSTRAINT FK_ratings_books FOREIGN KEY (book_id) REFERENCES books(book_id),
		CONSTRAINT FK_ratings_users FOREIGN KEY (user_id) REFERENCES users(user_id)
	);
	CREATE TABLE books_topics(
		book_topic_id INT IDENTITY(1,1) PRIMARY KEY,
		book_id INTEGER NOT NULL,
		topic_id INTEGER NOT NULL,
		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE(),
		CONSTRAINT FK_books_topics_topics FOREIGN KEY (topic_id) REFERENCES topics(topic_id),
		CONSTRAINT FK_books_topics_books FOREIGN KEY (book_id) REFERENCES books(book_id)
	);
	CREATE TABLE books_prog_langs(
		book_prog_lang_id INT IDENTITY(1,1) PRIMARY KEY,
		book_id INTEGER NOT NULL,
		prog_lang_id INTEGER NOT NULL,
		created_at DATETIME DEFAULT GETDATE(),
		updated_at DATETIME DEFAULT GETDATE(),
		CONSTRAINT FK_books_prog_langs_book FOREIGN KEY (book_id) REFERENCES books(book_id),
		CONSTRAINT FK_books_prog_langs_langs FOREIGN KEY (prog_lang_id) REFERENCES prog_langs(prog_lang_id)
	);


--creating the main user
USE master
CREATE LOGIN IT_ADMIN WITH PASSWORD = 'Hibali-Toab'
USE IT_Library
CREATE USER IT_ADMIN FOR LOGIN IT_ADMIN

ALTER ROLE db_owner ADD MEMBER IT_ADMIN;

EXEC sp_helprolemember 'db_owner';



