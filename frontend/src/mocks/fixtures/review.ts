import { GetReviewsMetaRes, GetReviewsRes, GetReviewSummaryRes } from '@/types/review/remote';

const getReviews = (): GetReviewsRes => ({
  reviews: [
    {
      id: 1,
      writerId: 1,
      petProfile: {
        id: 1,
        name: '에디',
        profileUrl: '',
        writtenAge: 132,
        writtenWeight: 72.3,
        breed: {
          id: 3,
          name: '말티즈',
          size: {
            id: 1,
            name: '대형견',
          },
        },
      },
      helpfulReaction: {
        count: 3,
        reacted: false,
      },
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
      writerId: 2,
      petProfile: {
        id: 2,
        name: '노아이즈',
        profileUrl: '',
        writtenAge: 102,
        writtenWeight: 72.3,
        breed: {
          id: 4,
          name: '시바',
          size: {
            id: 41,
            name: '대형견',
          },
        },
      },
      helpfulReaction: {
        count: 3,
        reacted: false,
      },
      rating: 3,
      date: '2023-07-01',
      comment: '제가 먹어봤는데 별로에요',
      tastePreference: '잘 안 먹어요',
      stoolCondition: '잘 모르겠어요',
      adverseReactions: ['눈물이 나요', '털이 푸석해요'],
    },
    {
      id: 3,
      writerId: 3,
      petProfile: {
        id: 11,
        name: '롤로노아 로지',
        profileUrl: '',
        writtenAge: 112,
        writtenWeight: 72.3,
        breed: {
          id: 33,
          name: '퍼그',
          size: {
            id: 121,
            name: '중형견',
          },
        },
      },
      helpfulReaction: {
        count: 3,
        reacted: false,
      },
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

const getReviewSummary = (): GetReviewSummaryRes => ({
  rating: {
    average: 3.7,
    rating: [
      {
        name: '5',
        percentage: 34,
      },
      {
        name: '4',
        percentage: 44,
      },
      {
        name: '3',
        percentage: 24,
      },
      {
        name: '2',
        percentage: 64,
      },
      {
        name: '1',
        percentage: 12,
      },
    ],
  },
  tastePreference: [
    {
      name: '정말 잘 먹음',
      percentage: 34,
    },
    {
      name: '잘 먹는 편',
      percentage: 54,
    },
    {
      name: '가끔 먹음',
      percentage: 34,
    },
    {
      name: '잘 안 먹음',
      percentage: 24,
    },
    {
      name: '전혀 안 먹음',
      percentage: 4,
    },
  ],
  stoolCondition: [
    {
      name: '딱딱',
      percentage: 34,
    },
    {
      name: '설사',
      percentage: 54,
    },
    {
      name: '말랑 딱딱',
      percentage: 34,
    },
    {
      name: '말랑',
      percentage: 24,
    },
    {
      name: '묽음',
      percentage: 14,
    },
    {
      name: '모르겠어요',
      percentage: 24,
    },
  ],
  adverseReaction: [
    {
      name: '호흡 이슈',
      percentage: 34,
    },
    {
      name: '침 흘림',
      percentage: 54,
    },
    {
      name: '화장실 자주 감',
      percentage: 34,
    },
    {
      name: '코 건조',
      percentage: 24,
    },
    {
      name: '구토',
      percentage: 14,
    },
    {
      name: '없음',
      percentage: 24,
    },
  ],
});

const reviewFixture = { getReviews, getReviewSummary, getReviewsMeta };

export default reviewFixture;
