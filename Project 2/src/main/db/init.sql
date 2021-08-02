drop table orders;
drop table users;
create table users(
    uid varchar primary key unique not null,
    urole varchar not null,
    uname varchar not null,
    upassword varchar not null
);

-- managers
insert into users values('admin13', 'employee', 'Manny Hinn', 'password1');
insert into users values('admin22', 'manager', 'Marcus Key', 'password1');
insert into users values('admin87', 'manager', 'Lana Lewis', 'password1');

-- employees
insert into users values('empl13', 'employee', 'Manny Hinn', 'password1');
insert into users values('empl44', 'employee', 'Brenda Powers', 'password1');
insert into users values('empl15', 'employee', 'John Adams', 'password1');

-- customers
insert into users values('jcombs@gmail.com', 'customer', 'Jonathan Combs', 'password');
insert into users values('chall@gmail.com', 'customer', 'Christian Hall', 'password');

insert into users values('dknight84@att.net', 'customer', 'David Knight', 'password');
insert into users values('clewis88@charter.net', 'customer', 'Charles Lewis', 'password');
insert into users values('zford84@gmail.com', 'customer', 'Zack Ford', 'password');
insert into users values('nloves22@yahoo.com', 'customer', 'Nathan Loves', 'password');
insert into users values('cruss21@outlook.com', 'customer', 'Chris Russ', 'password');
insert into users values('jsanchez36@gmail.com', 'customer', 'Jose Sanchez','password');
insert into users values('dpeters90@aol.com', 'customer', 'Don Peters', 'password');
insert into users values('bwilliams8@verizon.net', 'customer', 'Becky Williams', 'password');
insert into users values('klemon87@hotmail.com', 'customer', 'Karen Lemon', 'password');
insert into users values('lpeck24@gmail.com', 'customer', 'Larry Peck', 'password');
insert into users values('hsteins20@mail.com', 'customer', 'Harry Steins', 'password');
insert into users values('mevans43@aol.com', 'customer', 'Moe Evans', 'password');
insert into users values('tjackson5@hotmail.com', 'customer', 'Tina Jackson', 'password');
insert into users values('kchilds77@msn.com', 'customer', 'Kent Childs', 'password');
insert into users values('mtwain99@yahoo.com', 'customer', 'Martin Twain', 'password');

-- guest
insert into users values('guest', 'guest', 'Guest', 'irrelevant');


create table orders(
    oid bigserial primary key,
    uid varchar references users(uid),
    ostatus varchar not null,
    oitems varchar(120) not null,
    ototal money not null,
    otime timestamp default now(),
    otc timestamp,
    ocb varchar,
    onote varchar
);

-- 4 ostatus: 'order placed' 'ready for pickup' 'completed' 'canceled'
insert into orders values(default,'dknight84@att.net', 'completed', '(2)l-coke, (1)s-sprite, (1)l-fry l-sb milkshake, (1)chicken sandwich, (2)6-piece chicken wings', '25.36');
insert into orders values(default,'clewis88@charter.net', 'completed', '(2) l-dr pepper, (1)l-fry', '5.69');
insert into orders values(default,'zford84@gmail.com', 'completed', '(2)s-sprite, (1)l-fry l-c milkshake, (2)chicken waffles', '15.38');
insert into orders values(default,'nloves22@yahoo.com', 'canceled', '(1) l-coke, (2)l-fry, (1) chicken nuggets', '7.98');
insert into orders values(default,'cruss21@outlook.com', 'completed', '(1)l-diet coke, (2)s-sprite, (1)l-fry l-c milkshake, (2)chicken sandwich, (2)chicken waffles', '21.05');
insert into orders values(default,'jsanchez36@gmail.com', 'completed', '(2)l-dr pepper, (1)s-sprite, (2)l-fry, (1)chicken sandwich,(2) 6-piece chicken wings', '22.84');
insert into orders values(default,'dpeters90@aol.com', 'completed', '(2)s-sprite, (1)l-fry l-c milkshake, (2)chicken waffles', '12.33');
insert into orders values(default,'bwilliams8@verizon.net', 'completed', '(2) l-dr pepper, (1)l-fry', '5.90');
insert into orders values(default,'klemon87@hotmail.com', 'completed', '(2)L-sprite, (1)l-fry, (1)chicken waffles', '15.48');
insert into orders values(default,'lpeck24@gmail.com', 'completed', '(2)l-coke, (1)s-sprite, (2)l-fry, (1)l-sb milkshake, (1)chicken sandwich,(2) 6-piece chicken wings', '30.66');
insert into orders values(default,'hsteins20@mail.com', 'completed', '(2) l-dr pepper, (1)l-fry', '3.25');
insert into orders values(default,'mevans43@aol.com', 'completed', '(1) l-sprite, (1)l-fry, (1) chicken nuggets (1) chicken sandwich', '9.57');
insert into orders values(default,'tjackson5@hotmail.com', 'completed', '(1) l-sprite, (1)l-fry, (1) chicken nuggets (1) chicken sandwich', '7.85');
insert into orders values(default,'mtwain99@yahoo.com', 'completed', '(2)L-sprite, (1)l-fry, (1)chicken waffles', '21.45');
insert into orders values(default,'mtwain99@yahoo.com', 'completed', '(2)l-coke, (1)s-sprite, (2)l-fry, (1)l-sb milkshake, (1)chicken sandwich,(2) 6-piece chicken wings', '30.05');
insert into orders values(default,'guest', 'completed', '(2)L-sprite, (1)l-fry, (1)chicken waffles', '11.25');

insert into orders values(default,'guest', 'completed', '(2)l-coke, (1)s-sprite, (1)l-fry l-sb milkshake, (1)chicken sandwich, (2)6-piece chicken wings', '25.33');
insert into orders values(default,'guest', 'completed', '(1)l-diet coke, (2)s-sprite, (1)l-fry l-c milkshake, (2)chicken sandwich, (2)chicken waffles', '19.05');
insert into orders values(default,'guest', 'completed', '(2) l-dr pepper, (1)l-fry', '5.69');
insert into orders values(default,'guest', 'completed', '(1) l-sprite, (1)l-fry, (1) chicken nuggets (1) chicken sandwich', '9.87');
insert into orders values(default,'guest', 'canceled', '(2)s-sprite, (1)l-fry l-c milkshake, (2)chicken waffles', '13.59');
insert into orders values(default,'guest', 'completed', '(1) l-coke, (2)l-fry, (1) chicken nuggets', '7.85');
insert into orders values(default,'guest', 'completed', '(2)l-dr pepper, (1)s-sprite, (2)l-fry, (1)chicken sandwich,(2) 6-piece chicken wings', '21.30');
insert into orders values(default,'guest', 'completed', '(2)l-dr pepper, (2)l-fry ,(2) 10-piece chicken wings', '17.40');
insert into orders values(default,'guest', 'canceled', '(1) l-diet coke, (1)l-fry, (1) chicken nuggets (1) chicken sandwich', '13.20');
insert into orders values(default,'guest', 'completed', '(2)L-sprite, (1)l-fry, (1)chicken waffles', '15.99');
insert into orders values(default,'guest', 'completed', '(2)l-coke, (1)s-sprite, (2)l-fry, (1)l-sb milkshake, (1)chicken sandwich,(2) 6-piece chicken wings', '28.30');

insert into orders values(default,'dknight84@att.net', 'completed', '(2)l-coke, (1)s-sprite, (1)l-fry l-sb milkshake, (1)chicken sandwich, (2)6-piece chicken wings', '25.36');
insert into orders values(default,'clewis88@charter.net', 'completed', '(2) l-dr pepper, (1)l-fry', '5.69');
insert into orders values(default,'zford84@gmail.com', 'completed', '(2)s-sprite, (1)l-fry l-c milkshake, (2)chicken waffles', '15.38');
insert into orders values(default,'nloves22@yahoo.com', 'canceled', '(1) l-coke, (2)l-fry, (1) chicken nuggets', '7.98');
insert into orders values(default,'cruss21@outlook.com', 'completed', '(1)l-diet coke, (2)s-sprite, (1)l-fry l-c milkshake, (2)chicken sandwich, (2)chicken waffles', '21.05');
insert into orders values(default,'jsanchez36@gmail.com', 'completed', '(2)l-dr pepper, (1)s-sprite, (2)l-fry, (1)chicken sandwich,(2) 6-piece chicken wings', '22.84');
insert into orders values(default,'dpeters90@aol.com', 'completed', '(2)s-sprite, (1)l-fry l-c milkshake, (2)chicken waffles', '12.33');
insert into orders values(default,'bwilliams8@verizon.net', 'completed', '(2) l-dr pepper, (1)l-fry', '5.90');
insert into orders values(default,'klemon87@hotmail.com', 'completed', '(2)L-sprite, (1)l-fry, (1)chicken waffles', '15.48');
insert into orders values(default,'lpeck24@gmail.com', 'completed', '(2)l-coke, (1)s-sprite, (2)l-fry, (1)l-sb milkshake, (1)chicken sandwich,(2) 6-piece chicken wings', '30.66');
insert into orders values(default,'hsteins20@mail.com', 'completed', '(2) l-dr pepper, (1)l-fry', '3.25');
insert into orders values(default,'mevans43@aol.com', 'canceled', '(1) l-sprite, (1)l-fry, (1) chicken nuggets (1) chicken sandwich', '9.57');
insert into orders values(default,'tjackson5@hotmail.com', 'completed', '(1) l-sprite, (1)l-fry, (1) chicken nuggets (1) chicken sandwich', '7.85');
insert into orders values(default,'mtwain99@yahoo.com', 'completed', '(2)L-sprite, (1)l-fry, (1)chicken waffles', '21.45');
insert into orders values(default,'mtwain99@yahoo.com', 'completed', '(2)l-coke, (1)s-sprite, (2)l-fry, (1)l-sb milkshake, (1)chicken sandwich,(2) 6-piece chicken wings', '30.05');

insert into orders values(default,'guest', 'canceled', '(2)l-coke, (1)s-sprite, (1)l-fry l-sb milkshake, (1)chicken sandwich, (2)6-piece chicken wings', '25.33');
insert into orders values(default,'guest', 'completed', '(1)l-diet coke, (2)s-sprite, (1)l-fry l-c milkshake, (2)chicken sandwich, (2)chicken waffles', '19.05');
insert into orders values(default,'guest', 'ready for pickup', '(2) l-dr pepper, (1)l-fry', '5.69');
insert into orders values(default,'guest', 'ready for pickup', '(1) l-sprite, (1)l-fry, (1) chicken nuggets (1) chicken sandwich', '9.87');
insert into orders values(default,'guest', 'order placed', '(2)s-sprite, (1)l-fry l-c milkshake, (2)chicken waffles', '13.59');
insert into orders values(default,'guest', 'order placed', '(1) l-coke, (2)l-fry, (1) chicken nuggets', '7.85');
insert into orders values(default,'guest', 'order placed', '(2)l-dr pepper, (1)s-sprite, (2)l-fry, (1)chicken sandwich,(2) 6-piece chicken wings', '21.30');
insert into orders values(default,'guest', 'order placed', '(2)l-dr pepper, (2)l-fry ,(2) 10-piece chicken wings', '17.40');
insert into orders values(default,'guest', 'order placed', '(1) l-diet coke, (1)l-fry, (1) chicken nuggets (1) chicken sandwich', '13.20');
insert into orders values(default,'guest', 'order placed', '(2)L-sprite, (1)l-fry, (1)chicken waffles', '15.99');
insert into orders values(default,'guest', 'order placed', '(2)l-coke, (1)s-sprite, (2)l-fry, (1)l-sb milkshake, (1)chicken sandwich,(2) 6-piece chicken wings', '28.30');

-- to give a variety of outcomes
update orders set ocb = 'Brenda Powers', otc = now() WHERE mod(oid,2) <> 0 and ostatus = 'completed';
update orders set ocb = 'John Adams', otc = now() WHERE mod(oid,2) = 0 and ostatus = 'completed';
update orders set ocb = 'Lana Lewis', otc = now() WHERE ostatus = 'canceled';
update orders set otime = '2021-03-01 00:00:00' where oid between 1 and 16;
update orders set otime = '2021-05-01 00:00:00' where oid between 17 and 27;
update orders set otime = '2021-08-01 00:00:00' where oid between 28 and 42;
