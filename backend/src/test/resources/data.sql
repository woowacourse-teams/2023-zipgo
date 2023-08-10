INSERT INTO brand(name, image_url, nation, founded_year, has_research_center, has_resident_vet)
VALUES ('오리젠', 'https://intl.acana.com/wp-content/themes/acana2019/img/logo.png',
        '미국', 1985, true, true),
       ('퓨리나',
        'https://www.nestle.com/sites/default/files/styles/brand_logo/public/purina-logo-square-2023.png?h=a7e6d17b&itok=k6CCv7Sr',
        '미국', 1985, true, true);


insert into pet_food(brand_id, name, purchase_link, image_url, us_standard, eu_standard)
values (1, '[고집] 돌아온 배배',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/94087228?v=4', false, true),
       (1, '[고지집] 갈비 맛 모밀',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/76938931?v=4', true, false),
       (2, '[고집] 말미잘 맛 민무',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/76938931?v=4', true, true),
       (2, '[고집] 롤로노아 로지',
        'https://github.com/woowacourse-teams/2023-zipgo',
        'https://avatars.githubusercontent.com/u/76938931?v=4', false, false);

insert into functionality(name, pet_food_id)
values ('튼튼', 1),
       ('짱짱', 2),
       ('튼튼', 3),
       ('짱짱', 4);

insert into primary_ingredient(name, pet_food_id)
values ('닭고기', 1),
       ('말미잘', 2),
       ('닭고기', 3),
       ('말미잘', 4);

insert into member(name, email, profile_img_url)
values ('무민', 'moomin@gmail.com', '민무사진');
insert into member(name, email, profile_img_url)
values ('무민2', 'mooooomin22222@gmail.com', '민무사진2');


insert into review(member_id, pet_food_id, rating, comment, taste_preference, stool_condition, created_at)
values (1, 1, 5, '우리 아이랑 너무 잘 맞아요!', 'EATS_VERY_WELL', 'SOFT_MOIST', '2023-07-28');
insert into review(member_id, pet_food_id, rating, comment, taste_preference, stool_condition, created_at)
values (1, 1, 1, '우리 아이가 한 입 먹고 더 안 먹어요 ㅡ.ㅡ 책임지세요.', 'NOT_AT_ALL', 'DIARRHEA', '2023-07-28');

insert into adverse_reaction(adverse_reaction_type, review_id)
values ('NONE', 1);
insert into adverse_reaction(adverse_reaction_type, review_id)
values ('TEARS', 2);
insert into adverse_reaction(adverse_reaction_type, review_id)
values ('VOMITING', 2);
