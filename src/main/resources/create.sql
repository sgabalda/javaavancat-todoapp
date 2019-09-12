create table USERS_TODO
(
    userid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT USER_TODO_PK PRIMARY KEY,
    USERNAME VARCHAR(256),
    PASSWORD VARCHAR(256),
    ADMINUSER BOOLEAN
);

create table TASK_TODO
(
    taskid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT TAKS_TODO_PK PRIMARY KEY,
    userid INTEGER NOT NULL,
    DESCRIPTION VARCHAR(256),
    ACABADA BOOLEAN,
    CONSTRAINT userid_todo_ref FOREIGN KEY (userid) REFERENCES USERS_TODO(userid)
);
