insert into keyword(id, name)
values (1, 'diet');

insert into brand(id, name, nation, founded_year, has_research_center, has_resident_vet, image_url)
values (1, '오리젠', '미국', 1985, true, true, 'https://intl.acana.com/wp-content/themes/acana2019/img/logo.png');

insert into brand(id, name, nation, founded_year, has_research_center, has_resident_vet)
values (2, '퓨리나', '미국', 1985, true, true);

insert into pet_food(brand_id, keyword_id, name, purchase_link, image_url, us_standard, eu_standard, functionality,
                     primary_ingredients)
values (1, 1, '[고집] 돌아온 배배',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/94087228?v=4', true, true, '튼튼,짱짱', '닭고기,쌀'),
       (2, 1, '[고집] 갈비 맛 모밀',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/76938931?v=4', true, true, null, '닭고기'),
       (1, 1, '[고집] 말미잘 맛 민무',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/76938931?v=4', true, true, null, '말미잘');

insert into member DEFAULT VALUES;
insert into member DEFAULT VALUES;

insert into review(member_id, pet_food_id, rating, comment, taste_preference, stool_condition, created_at)
values (1, 1, 5, '우리 아이랑 너무 잘 맞아요!', 'EATS_VERY_WELL', 'SOFT_MOIST', '2023-07-28');
insert into review(member_id, pet_food_id, rating, comment, taste_preference, stool_condition, created_at)
values (1, 1, 1, '우리 아이가 한 입 먹고 더 안 먹어요 ㅡ.ㅡ 책임지세요.', 'NOT_AT_ALL', 'DIARRHEA', '2023-07-28');

insert into adverse_reaction(name, review_id)
values ('눈물이 나요', 2);
insert into adverse_reaction(name, review_id)
values ('먹고 토해요', 2);
