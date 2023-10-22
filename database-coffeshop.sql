CREATE DATABASE coffeeshop;

CREATE TABLE User(
	id bigint auto_increment primary key,
	date_of_birth date not null,
	email varchar(255) not null,
	full_name varchar(255) not null, 
	is_actived varchar(255) not null, 
	password varchar(255) not null,
	point bigint not null,
	reward varchar(255) not null, 
	username varchar(255) not null
);

CREATE TABLE Role(
	id bigint primary key,
    name varchar(255) not null
);

CREATE TABLE User_Role(
    user_id bigint,
    role_id bigint,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (role_id) REFERENCES Role(id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE Payment(
	id bigint auto_increment primary key,
    name varchar(255) not null
);

CREATE TABLE Category(
	id bigint auto_increment primary key,
    name varchar(255) not null
);

CREATE TABLE Product(
	id bigint auto_increment primary key,
    image varchar(255),
    name varchar(255),
    price double,
    category_id bigint,
    foreign key (category_id) REFERENCES Category(id)
);

CREATE TABLE Delivery_info(
	id bigint auto_increment primary key,
	address varchar(255),
	is_default varchar(255),
	note varchar(255),
	phone_number varchar(255),
	receiver varchar(255),
	user_id bigint,
    foreign key (user_id) REFERENCES User(id)
);

CREATE TABLE Voucher(
	id bigint auto_increment primary key,
	code varchar(255),
	description varchar(255),
	expiry_date date,
	name varchar(255), 
	start_date date,
	value double
);

CREATE TABLE `order`(
	id bigint auto_increment primary key,
    order_date date,
	user_id bigint,
	voucher_id bigint, 
	payment_id bigint,
	foreign key (user_id) REFERENCES User(id),
    foreign key (voucher_id) REFERENCES Voucher(id),
    foreign key (payment_id) REFERENCES Payment(id)
);

CREATE TABLE Bill(
	id bigint auto_increment primary key,
	amount double,
	order_id bigint,
	foreign key (order_id) REFERENCES `Order`(id)
);

CREATE TABLE Feedback(
	id bigint auto_increment primary key,
	content varchar(255),
	title varchar(255),
	order_id bigint,
    foreign key (order_id) REFERENCES `Order`(id)
);

CREATE TABLE item(
	id bigint auto_increment primary key,
	ice varchar(255),
	price_in double,
	quantity int,
	size varchar(255),
	sugar varchar(255), 
	type varchar(255),
	order_id bigint,
	product_id bigint,
	foreign key (order_id) REFERENCES `Order`(id),
    foreign key (product_id) REFERENCES Product(id)
);

CREATE TABLE Shipment(
	id bigint auto_increment primary key,
    is_completed varchar(255),
	shipper_id varchar(255),
	shipper_name varchar(255), 
	shipper_phone varchar(255), 
	delivery_info_id bigint,
	order_id bigint,
    foreign key (delivery_info_id) REFERENCES Delivery_info(id),
    foreign key (order_id) REFERENCES `order`(id)
);


-- password: 1234 (đã được mã hóa)
INSERT INTO user values(1,'2003-04-15','nttue03@gmail.com','Nguyen Tai Tue','true','$2a$10$P7eZP83B9dCNCQ/J9Vjz6OO9IIv9AQIelNABl3u.uuw1e7JJxYUHi',0,'new','admin');

INSERT INTO Role values(101,'ROLE_ADMIN');
INSERT INTO Role values(102,'ROLE_USER');
INSERT INTO Role values(103,'ROLE_SHIPPER');

INSERT INTO User_Role Values(1,101);

INSERT INTO category Values(1,'Cà Phê');
INSERT INTO category Values(2,'CloudFee');
INSERT INTO category Values(3,'CloudTea');
INSERT INTO category Values(4,'Trà');
INSERT INTO category Values(5,'Thức uống đá xay');

INSERT INTO Product Values(1,'https://product.hstatic.net/1000075078/product/1686716532_dd-suada_6053980e5bff4947b65fcd6e535c4705.jpg','Đường Đen Sữa Đá',45000,1);
INSERT INTO Product Values(2,'https://product.hstatic.net/1000075078/product/1675355354_bg-tch-sua-da-no_6fd8102d043c4cf8b66874f51af9ea74.jpg','Coffee Sữa Đá',39000,1);
INSERT INTO Product Values(3,'https://product.hstatic.net/1000075078/product/1639377904_bac-siu_1b89c9285a414913a152f93277991c74.jpg','Bạc Sỉu',29000,1);
INSERT INTO Product Values(4,'https://product.hstatic.net/1000075078/product/1639377797_ca-phe-den-da_dbae180ab4e0437b97629fa9dcbba296.jpg','Cà Phê Đen Đá',29000,1);
INSERT INTO Product Values(5,'https://product.hstatic.net/1000075078/product/1686716537_dd-latte_e10fbeb0a9be4002844d9203fcab907e.jpg','Đường Đen Marble Latte',55000,1);
INSERT INTO Product Values(6,'https://product.hstatic.net/1000075078/product/1675329314_bg-cloudfee-caramel_a4889bc29336482cbebf19ee35a16ae8.jpg','CloudFee Caramel',49000,2);
INSERT INTO Product Values(7,'https://product.hstatic.net/1000075078/product/caramelmacchiatonong_168039_cf24362398084ac2af9fce3cf245d9b7_large.jpg','CloudFee Hà Nội',49000,2);
INSERT INTO Product Values(8,'https://product.hstatic.net/1000075078/product/capu-da_487470_1d9c592fd64b45db86fcef1fe6ec2b72_large.jpg','CloudTea Oolong Nướng Kem Cheese',55000,3);
INSERT INTO Product Values(9,'https://product.hstatic.net/1000075078/product/cappuccino_621532_55d1fc87f07d44a8b784460f1963433b_large.jpg','CloudTea Oolong Nướng Kem Dừa Đá Xay',55000,3);
INSERT INTO Product Values(10,'https://product.hstatic.net/1000075078/product/1675329120_coldbrew-pbt_7f3bf666180b4f498a0d729192f5f568_large.jpg','Trà Đào Cam Sả - Nóng',59000,4);
INSERT INTO Product Values(11,'https://product.hstatic.net/1000075078/product/tra-sen_905594_4c3a17b027de4acd9a2ce97c574c8539.jpg','Trà Hạt Sen - Đá',39000,4);
INSERT INTO Product Values(12,'https://product.hstatic.net/1000075078/product/classic-cold-brew_239501_76293c2c7dc64db9946df2acaf7e7df6.jpg','Trà Hạt Sen - Nóng',49000,4);
INSERT INTO Product Values(13,'https://product.hstatic.net/1000075078/product/hong-tra-sua-tran-chau_326977_afa218e3793e414b9e4e1f11d66e39c8.jpg','Hồng Trà Sữa Trân Châu',55000,4);
INSERT INTO Product Values(14,'https://product.hstatic.net/1000075078/product/hong-tra-sua-nong_941687_479ee72ea4724ee69416c6d45bf05132.jpg','Hồng Trà Sữa Nóng',55000,4);
INSERT INTO Product Values(15,'https://product.hstatic.net/1000075078/product/tra-sua-mac-ca_377522_c6e94851cbfd4173ac798932992ca356.jpg','Trà Sữa Mắc Ca Trân Châu',55000,4);
INSERT INTO Product Values(16,'https://product.hstatic.net/1000075078/product/1686716517_kombucha-dao_6e056e2f19364e4c92cb2ed36a8c8986.jpg','Smoothie Xoài Nhiệt Đới Granola',65000,5);

INSERT INTO Delivery_info Values(1,'Ba Vì, Hà Nội','true','Giao hàng vào 7h sáng','0383291503','Tài Tuệ', 1);

INSERT INTO Payment Values (1 , 'Nhận hàng trước khi thanh toán');
INSERT INTO Payment Values (2 , 'Thanh toán qua VNPAY pay');

INSERT INTO `order` Values(1,'2023-07-14',1,1,null);

INSERT INTO shipment values(1,'false','HN001','Nguyen Tai Tue','0383291503',1,1);

INSERT INTO item Values(1,'30%',45000,1,'XL','30%','Cà phê',1,1);

INSERT INTO Bill Values(1,45000,1);







