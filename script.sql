create TABLE Users(
user_id serial  NOT NULL,
user_name varchar(50) NOT NULL,
address varchar(255) NULL,
PRIMARY KEY(user_id)
);
CREATE SEQUENCE user_id_seq;
ALTER TABLE Users ALTER user_id SET DEFAULT NEXTVAL('user_id_seq');
create TABLE Accounts(
account_id serial  NOT NULL,
user_id serial NOT NULL,
balance decimal(13,3)  NOT NULL,
currency varchar(10) CHECK
(currency IN ('BYN', 'RUR', 'EUR', 'USD')),
PRIMARY KEY(account_id),
FOREIGN KEY(user_id) REFERENCES Users (user_id)
);
CREATE SEQUENCE account_id_seq;
ALTER TABLE Accounts ALTER account_id SET DEFAULT NEXTVAL('account_id_seq');

create TABLE Transactions(
transaction_id serial  NOT NULL,
account_id serial NOT NULL,
amount decimal(12,3) NOT NULL,
PRIMARY KEY(transaction_id),
FOREIGN KEY(account_id) REFERENCES Accounts (account_id)
);

CREATE SEQUENCE transaction_id_seq;
ALTER TABLE Transactions ALTER transaction_id SET DEFAULT NEXTVAL('transaction_id_seq');
