CREATE TABLE transaction_detail (
	id uuid NOT NULL,
	transaction_id varchar(65),
	account_id varchar(31),
    created_at timestamp,
	currency varchar(3),
	amount numeric(11,2)
);
