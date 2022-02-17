
update typecho_users set created = null ;
alter table  typecho_users  modify column created datetime default now() ;
