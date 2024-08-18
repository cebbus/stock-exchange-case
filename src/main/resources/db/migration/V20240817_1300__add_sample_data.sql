insert into stock (name, description, current_price, last_update) values ('HEPS', 'Hepsiburada', 3.08, parsedatetime('17-08-2024 15:48:52.69', 'dd-MM-yyyy hh:mm:ss.SS'));

insert into stock_exchange (name, description, live_in_market) values ('BIST', 'Borsa Istanbul', false);
insert into stock_exchange (name, description, live_in_market) values ('NYSE', 'The New York Stock Exchange', false);
insert into stock_exchange (name, description, live_in_market) values ('NASDAQ', 'National Association of Securities Dealers Automated Quotations', false);

insert into stock_exchange_stock_mapping (exchange_id, stock_id) values (1, 1);
insert into stock_exchange_stock_mapping (exchange_id, stock_id) values (3, 1);