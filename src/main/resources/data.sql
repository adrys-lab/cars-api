/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01');

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04');

insert into driver (id, date_created, deleted, online_status, password, username) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05');

insert into driver (id, date_created, deleted, online_status, password, username) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08');

------------------ START ADRIAN SQL ADDITION ------------------

-------------------------------------------------
-- Create manufacturer table
-------------------------------------------------
insert into manufacturer (id, date_created, name, description, country) values (1, now(), 'Mercedes', 'Daimeler Best company', 'Germany');
insert into manufacturer (id, date_created, name, description, country) values (2, now(), 'BMW', 'BMW Best cars in the world', 'Germany');
insert into manufacturer (id, date_created, name, description, country) values (3, now(), 'SEAT', 'SEAT is the best company', 'Spain');
insert into manufacturer (id, date_created, name, description, country) values (4, now(), 'FIAT', 'FIAT regular cars', 'Italy');

-------------------------------------------------
-- Create car
-------------------------------------------------
insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (1, now(), false, '9845AAA', 5, false, 3.50, 'HYBRID', 1);
insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (2, now(), false, '9867XXB', 2, true, 5, 'FUEL', 2);
insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (3, now(), false, '1298ABC', 2, true, 8.3, 'ELECTRIC', 3);
insert into car (id, date_created, deleted, license_plate, seat_count, convertible, rating, engine_type, manufacturer) values (4, now(), false, '7402MUX', 2, true, 9, 'ELECTRIC', 4);

-------------------------------------------------
-- Create Simple User table
----- remember all user/pass pairs have the same value ex: user1/user1, user2/user2, user3/user3...
INSERT INTO API_user (ID, username, password) VALUES (1, 'user1', '$2a$10$iGSBHVvvJLfUeDHJfwA53O9NSxnVIZv4ojwduCN8.3EBkXIwfbpYW');
INSERT INTO API_user (ID, username, password) VALUES (2, 'user2', '$2a$10$8sO0Z/7Cgsn8ZkXjhEUy0u99GvoLDShjv1C89YCBve5msVg73LOpG');
INSERT INTO API_user (ID, username, password) VALUES (3, 'user3', '$2a$10$.7dvKO7VuCW9R6/WMBx1h.7viXL0hbFFoUcNUfZzlVZcNAzM/kUn2');
INSERT INTO API_user (ID, username, password) VALUES (4, 'user4', '$2a$10$/HPufYgd6p5uFT4ynW../uPAwINvp96qKzofkdX5PWX3s6R6LEW/.');
INSERT INTO API_user (ID, username, password) VALUES (5, 'user5', '$2a$10$235h7WBMdxwDII6OfSYfteiVFIMdEXZ6MXpCvjbcDMcKFDWPA2pzO');
INSERT INTO API_user (ID, username, password) VALUES (6, 'user6', '$2a$10$1AwdLDCaDei3OhqI23LtoeSjHHSdRLrckoWKllBWf7/tPyPQ9dJoO');

INSERT INTO ROLE (ID, name) VALUES (1, 'ROLE_READER');
INSERT INTO ROLE (ID, name) VALUES (2, 'ROLE_WRITER');
INSERT INTO ROLE (ID, name) VALUES (3, 'ROLE_UNAUTHORIZED');

INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (1, 2);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (3, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (4, 1);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (5, 3);
INSERT INTO USER_ROLE (user_id, role_id) VALUES (6, 3);

------------------ FINISH ADRIAN SQL ADDITION ------------------