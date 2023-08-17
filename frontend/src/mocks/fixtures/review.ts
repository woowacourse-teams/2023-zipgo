import { GetReviewsMetaRes, GetReviewsRes } from '@/types/review/remote';

const getReviews = (): GetReviewsRes => ({
  reviews: [
    {
      id: 1,
      reviewerName: '에디',
      rating: 5,
      date: '2023-05-26',
      comment:
        '제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요! 제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요! 제가 먹어봤는데 폼 미쳤음 너무 맛있음 매일 먹어도 안 질릴 것 같아요. 앞으로 도시락으로 싸갈 생각입니다. 짱짱 맛있어요 우리집 강아지도 잘 먹어요!',
      tastePreference: '정말 잘 먹어요',
      stoolCondition: '촉촉 말랑해요',
      adverseReactions: ['없어요'],
    },
    {
      id: 2,
      reviewerName: '노아이즈',
      rating: 3,
      date: '2023-07-01',
      comment: '제가 먹어봤는데 별로에요',
      tastePreference: '잘 안 먹어요',
      stoolCondition: '잘 모르겠어요',
      adverseReactions: ['눈물이 나요', '털이 푸석해요'],
    },
    {
      id: 3,
      reviewerName: '롤로노아 로지',
      rating: 4,
      date: '2023-07-26',
      comment: '저희집 베베가 잘 먹어요',
      tastePreference: '잘 먹는 편이에요',
      stoolCondition: '설사를 해요',
      adverseReactions: ['없어요'],
    },
  ],
});

const getReviewsMeta = (): GetReviewsMetaRes => ({
  petSizes: [
    {
      id: 1,
      name: '소형견',
    },
    {
      id: 2,
      name: '중형견',
    },
    {
      id: 3,
      name: '대형견',
    },
  ],
  sortBy: [
    {
      id: 1,
      name: '최신순',
    },
    {
      id: 2,
      name: '별점 높은 순',
    },
    {
      id: 3,
      name: '별점 낮은 순',
    },
    {
      id: 4,
      name: '도움이 되는 순',
    },
  ],
  ageGroups: [
    {
      id: 1,
      name: '퍼피',
    },
    {
      id: 2,
      name: '어덜트',
    },
    {
      id: 3,
      name: '시니어',
    },
  ],
  breeds: [
    {
      id: 1,
      name: '말티즈',
    },
    {
      id: 2,
      name: '진돗개',
    },
    {
      id: 3,
      name: '리트리버',
    },
  ],
});

const reviewFixture = { getReviews, getReviewsMeta };

export default reviewFixture;
