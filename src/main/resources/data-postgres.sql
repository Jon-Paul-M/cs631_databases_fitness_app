-- This is where inalizing and sample data will go



INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (01, 'Circuit Training', 'Burn fat fast with high intensity intervals');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (02, 'Cardio Tone', 'Come join the fun in this high energy, fat burning class, with easy-to-follow routines');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (03, 'Core and More', 'Functional core training allows you to practice movement that provides optimal motion for daily tasks');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (04, 'Zumba', 'This exhilarating, easy-to-follow, Latin-inspired, calorie-burning dance fitness-party will move you');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (05, 'Belly Dancing', 'Shimmy your way to a sculpted body by shaking your hips and moving your abdominal muscles');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (06, 'Pole Dancing', 'Channel your inner exotic dancer and limber up for some fun while getting a serious workout for your arms, back, abs, and legs with this pole routine');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (07, 'Boot Camp', 'You can command Power, Strength and Agility with this military inspired circuit workout that will push you to your limits');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (08, 'Spin', 'Stay in the zone with this intense cycling');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (09, 'Kickboxing', 'Kick, punch, bob and weave your way to a higher fitness level');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (10, 'Mat Pilates', 'Increase your flexibility as you flow through a series of dynamic movements that restore balance to core muscles of the lower back and abdominals');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (11, 'Tai Chi', 'Discover the benefits of this ancient form of martial arts which will improve balance, agility, strength, and coordination');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (12, 'Step Sculpt', 'A great step workout followed by intense muscle conditioning with light weights and challenging core work');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (13, 'Hatha Yoga', 'Focus on flexabilty and strenth on this yoga class');
INSERT INTO EXERCISE (EXERCISE_ID, NAME, DESCRIPTION) VALUES (14, 'Hot Hatha', 'Come prepared to sweat practicing in a room heated to 105 degrees');

INSERT INTO ROOM (ROOM_ID, CAPACITY, ROOM_NUMBER, BUILDING_NAME) VALUES (01, 25, 'Bike A', 'Central Gymansium');
INSERT INTO ROOM (ROOM_ID, CAPACITY, ROOM_NUMBER, BUILDING_NAME) VALUES (02, 13, 'Studio A', 'Central Gymansium');
INSERT INTO ROOM (ROOM_ID, CAPACITY, ROOM_NUMBER, BUILDING_NAME) VALUES (03, 32, 'Cardio B', 'Alumni Hall');

INSERT INTO MEMBERSHIP (MEMBERSHIP_ID, MEMBERSHIP_TYPE, FEE, ADVANTAGES) VALUES (01, 'ONE WEEK TRIAL', '0.00', 'Fitness coaching\nGroup training\nTwice weekly\nFitness Calculator\nCardio Floor');
INSERT INTO MEMBERSHIP (MEMBERSHIP_ID, MEMBERSHIP_TYPE, FEE, ADVANTAGES) VALUES (02, '3 MONTHS', '299.00', 'Fitness coaching\nGroup training\nTwice weekly\nFitness Calculator\nCardio Floor');
INSERT INTO MEMBERSHIP (MEMBERSHIP_ID, MEMBERSHIP_TYPE, FEE, ADVANTAGES) VALUES (03, '6 MONTHS', '450.00', 'Flexible coaching\nSemi-private training\nTimes to suit you\nPilates\nTennis');
INSERT INTO MEMBERSHIP (MEMBERSHIP_ID, MEMBERSHIP_TYPE, FEE, ADVANTAGES) VALUES (04, '12 MONTHS', '980.00', 'Flexible coaching\nSemi-private training\nTimes to suit you\nPilates\nTennis');


-- H2 has a command <script into 'mydatafilename.sql'>  Saves a lot of time, neat.
INSERT INTO PERSON(USER_ID, EMAIL, NAME, ENABLED, TOKEN_EXPIRED, PASSWORD_HASH) VALUES
(1, 'oria@njit.edu', 'Vincent Oria', TRUE, FALSE, '$2a$10$AIqpc4JhuHuXOdPchYgeZuVfU/vP3FJDUJk/NP8zG8hHqfoa7DWsG'),
(220, 'admin@test.com', 'Admin', TRUE, FALSE, '$2a$10$SqJDQRbHTmusFH3PGXtftOgSrHppGFcddtQcNClzuM2fvxeRZG01e'),
(221, 'hourlyinstructor@test.com', 'Hourly Instructor', TRUE, FALSE, '$2a$10$TCBgiq3EgTnWmqE7bZse4e8bqJHlaI82kTIf.omY/2fvXUPOBWTse'),
(222, 'salariedinstructor@test.com', 'Salaried Instructor', TRUE, FALSE, '$2a$10$9bMFwzhSGas3/iThETi3Z.uX7I/aXW4LlRowe25G9E6ATN/xFcEfS'),
(223, 'instructormember@test.com', 'Hourly Instructor & Member', TRUE, FALSE, '$2a$10$kCQ1NL/1g0.7O53irXT6l.tvIaCa.tiRhaPXY0RLSRhIJlbfDT.Me'),
(224, 'member@test.com', 'Regular Member', TRUE, FALSE, '$2a$10$oyPE.PGq9s2Ov9yz7pSX7eZ3A/XheGU8Kq2Ikh3OP0qtUfrSRilSm');


INSERT INTO ROLE(ROLE_ID, NAME) VALUES
(217, 'ROLE_ADMIN'),
(218, 'ROLE_INSTRUCTOR'),
(219, 'ROLE_MEMBER');

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID) VALUES
(220, 217),
(221, 218),
(222, 218),
(223, 218),
(223, 219),
(224, 219);

INSERT INTO PRIVILEGE(PRIVILEGE_ID, NAME) VALUES
(200, 'CHANGE_ALL_PASSWORD_PRIVILEGE'),
(201, 'CHANGE_SELF_PASSWORD_PRIVILEGE'),
(202, 'ADMIN'),
(203, 'REGISTER_MEMBER'),
(204, 'REGISTER_INSTRUCTOR'),
(205, 'REGISTER_SELF_FOR_CLASS'),
(206, 'REGISTER_MEMBER_FOR_CLASS'),
(207, 'CREATE_CLASS'),
(208, 'VIEW_ALL_DETAILS_CLASS'),
(209, 'CANCEL_CLASS'),
(210, 'CANCEL_OWN_CLASS'),
(211, 'DELETE_MEMBER'),
(212, 'DELETE_CLASS'),
(213, 'VIEW_ALL_MEMBERS'),
(214, 'VIEW_ALL_MEMBERS_OWN_CLASS'),
(215, 'MODIFY_ALL_MEMBERS'),
(216, 'MODIFY_SELF_MEMBER');

INSERT INTO ROLES_PRIVILEGES(ROLE_ID, PRIVILEGE_ID) VALUES
(217, 202),
(217, 200),
(217, 201),
(217, 203),
(217, 204),
(217, 205),
(217, 206),
(217, 207),
(217, 209),
(217, 210),
(217, 211),
(217, 212),
(217, 213),
(217, 214),
(217, 208),
(217, 215),
(217, 216),
(218, 201),
(218, 216),
(218, 210),
(218, 214),
(219, 201),
(219, 205),
(219, 216);

INSERT INTO HOURLY_INSTRUCTOR(USER_ID, HOURS, HOURLY_WAGE) VALUES
(1, 20, 22.75),
(221, 0, 1.00),
(223, 0, 1.00);

INSERT INTO SALARIED_INSTRUCTOR(USER_ID, SALARY) VALUES
(222, 1.00);

INSERT INTO MEMBER(USER_ID, ADDRESS1, ADDRESS2, CITY, COUNTY, STATE, POSTAL_CODE, REGISTRATION_DATE, MEMBERSHIP_ID) VALUES
(224, 'Address 1', 'Address 2', 'City', 'county', 'State', '12345', TIMESTAMP '2018-04-29 19:58:06.584', 1);