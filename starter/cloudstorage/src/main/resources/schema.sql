CREATE TABLE IF NOT EXISTS USER (
  userId INT PRIMARY KEY auto_increment,
  username VARCHAR(20) UNIQUE,
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTE (
    noteId INT PRIMARY KEY auto_increment,
    noteTitle VARCHAR(20),
    noteDescription VARCHAR (1000),
    userId INT,
    foreign key (userId) references USER(userId)
);

CREATE TABLE IF NOT EXISTS FILE (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contentType VARCHAR,
    fileSize VARCHAR,
    userId INT,
    fileData BLOB,
    foreign key (userId) references USER(userId)
);

CREATE TABLE IF NOT EXISTS CREDENTIAL (
    credentialId INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USER(userid)
);
