insert into user_tb(username, password, email) values('ssar', '$2a$10$PObcHzBVPoWxR/AiEDrwmOaH/nZk94p3TSVQRZRF6MUHV2vYU8wUy', 'ssar@nate.com');
insert into user_tb(username, password, email) values('cos', '$2a$10$PObcHzBVPoWxR/AiEDrwmOaH/nZk94p3TSVQRZRF6MUHV2vYU8wUy', 'cos@nate.com');


insert into board_tb(title, content, user_id, created_at) values('제목1', '내용1', 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목2', '내용2', 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목2', '내용3', 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목22', '내용4', 2, now());
insert into board_tb(title, content, user_id, created_at) values('제목5', '내용5', 2, now());
insert into board_tb(title, content, user_id, created_at) values('제목23', '내용3', 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목2', '내용3', 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목2222', '내용3', 1, now());
insert into board_tb(title, content, user_id, created_at) values('제목21', '내용3', 1, now());



insert into reply_tb(comment, user_id, board_id) values('댓글1', '1', '1');
insert into reply_tb(comment, user_id, board_id) values('댓글2', '1', '2');
insert into reply_tb(comment, user_id, board_id) values('댓글3', '1', '3');
insert into reply_tb(comment, user_id, board_id) values('댓글4', '2', '5');
insert into reply_tb(comment, user_id, board_id) values('댓글5', '2', '5');
