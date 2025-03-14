select * from topics
insert into topics (topic_name) values ('dzz')
update topics set topic_deleted = 1 where topic_id = 6
select GETDATE()