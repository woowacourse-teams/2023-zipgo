insert into keyword(id, name)
values (1, 'diet');

insert into pet_food(keyword_id, name, purchase_link, image_url)
values (null, '[고집] 돌아온 배배',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/94087228?v=4'),
       (1, '[고집] 갈비 맛 모밀',
        'https://github.com/woowacourse-teams/2023-zipgo',
'https://avatars.githubusercontent.com/u/76938931?v=4');

insert into member DEFAULT VALUES;

insert into review(member_id, pet_food_id, rating, comment, taste_preference, stool_condition, created_at)
values (1, 1, 5, '우리 아이랑 너무 잘 맞아요!', 'EATS_VERY_WELL', 'SOFT_MOIST', '2023-07-28');
insert into review(member_id, pet_food_id, rating, comment, taste_preference, stool_condition, created_at)
values (1, 1, 1, '우리 아이가 한 입 먹고 더 안 먹어요 ㅡ.ㅡ 책임지세요.', 'NOT_AT_ALL', 'DIARRHEA', '2023-07-28');

insert into adverse_reaction(name, review_id) values('눈물이 나요', 2);
insert into adverse_reaction(name, review_id) values('먹고 토해요', 2);
