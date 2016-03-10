create table proiect.article
      (
         id           number(9) primary key,
         article_no   number (5) not null,
         article_name varchar2(100)
      );
create table proiect.supplier
      (
         id             number(9) primary key,
         supplier_no    number (5) not null,
         supplier_name  varchar2(100),
         is_vat_extempt number(1),
         vat            number (5)
      );
create table proiect.order_item
      (
         id          number(9) primary key,
         id_article  number (9) not null constraint fk_art_id references proiect.article (id),
         id_supplier number (9) not null constraint fk_supp_id references proiect.supplier (id),
         quantity    number(5)
      );
create table proiect.orders
      (
         id            number(9) primary key,
         delivery_date date,
         valid_from    date,
         valid_to      date,
         available_at  number (1)
      );
create table proiect.orders_items
      (
         id_order not null constraint fk_order_id references proiect.orders (id) ,
         id_item not null constraint fk_item_id references proiect.order_item (id)
      );
create table proiect.stock
      (
         id number(9) primary key,
         id_article not null constraint fk_article_id references proiect.article (id) ,
         quantity number (5)
      );
	  

ALTER TABLE adi_test_db.order_item ADD id_order BIGINT;

ALTER TABLE adi_test_db.order_item ADD CONSTRAINT fk_order_id_1 FOREIGN KEY (id_order) REFERENCES adi_test_db.orders(id);

ALTER TABLE adi_test_db.orders ADD discriminator VARCHAR(20) NOT NULL;

ALTER TABLE adi_test_db.supplier ADD discriminator VARCHAR(20) NOT NULL;

delete from adi_test_db.order_item;
delete from adi_test_db.orders;
delete from adi_test_db.supplier;
delete from adi_test_db.article;
