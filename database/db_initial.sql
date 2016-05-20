use cstore_db;

insert into accounts (first_name, last_name, hashed_password, 
email, display_name, fb_link, twitter_link, google_plus_link, 
country, city,  about_me, type, is_banned)
values 
('admin', 'adminadmin', 
'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin_email', 
'admin_display_name', 'admin_fb_link_1', 'admin_twitter_link_1', 'admin_google_plus_link_1', 
'admin_country_1', 'admin_city_1', 'admin_about_me_1', 'ADMIN', '0'),

('user', 'useruser', 
'd033e22ae348aeb5660fc2140aec35850c4da997', 'user_email', 
'user_display_name', 'user_fb_link_1', 'user_twitter_link_1', 'user_google_plus_link_1', 
'user_country_1', 'user_city_1', 'user_about_me_1', 'ADMIN', '0');

insert into post (account_id, youtube_link, title, description, post_time)
values
(1, 'youtube_link_1', 'title_1', 'description_1', now()),
(2, 'youtube_link_2', 'title_2', 'description_2', now()),
(2, 'youtube_link_3', 'title_3', 'description_3', now());

insert into plus (post_id, account_id)
values
(3,2),
(2,1),
(1,2);

insert into minus (post_id, account_id)
values
(2,2),
(1,1),
(3,2);

insert into comment (post_id, account_id, comment_text, add_time)
values
(1,2,'commentOfUser2onPost1',now()),
(2,2,'commentOfUser3onPost2',now()),
(3,1,'commentOfUser1onPost3',now()),
(3,2,'commentOfUser3onPost3',now()),
(2,1,'commentOfUser1onPost2',now());

insert into tag (tag)
values
('tag_1'),
('tag_2'),
('tag_3'),
('tag_4');

insert into post_tag (post_id, tag_id)
values
(1,1),
(1,2),
(2,2),
(3,2),
(3,4),
(2,3);

insert into follow (follower_id, following_id)
values
(1,2),
(2,1)