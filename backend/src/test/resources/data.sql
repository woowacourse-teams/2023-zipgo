insert into keyword(id, name)
values (1, 'diet');

insert into brand(id, name, nation, founded_year, has_research_center, has_resident_vet)
values (1, '오리젠', '미국', 1985, true, true);

insert into pet_food(brand_id, keyword_id, name, purchase_link, image_url, us_standard, eu_standard, functionality)
values (1, null, '[고집] 돌아온 배배',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/94087228?v=4', true, true, '튼튼,짱짱'),
       (1, 1, '[고집] 갈비 맛 모밀',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/76938931?v=4', true, true, null);

insert into member(id)
values (1);

insert into review(member_id, pet_food_id, ratings, comment, taste_preference, stool_condition)
values (1, 1, 5, '잘 먹네요', 'EATS_VERY_WELL', 'SOFT_MOIST'),
       (1, 1, 3, '잘 안 먹네요', 'NOT_AT_ALL', 'SOFT_MOIST'),
       (1, 1, 4, '잘 먹네요', 'EATS_MODERATELY', 'SOFT_MOIST');
