import { GetFoodDetailRes, GetFoodListRes } from '@/types/food/remote';

const getFoodList = (): GetFoodListRes => ({
  petFoods: [
    {
      id: 1,
      purchaseUrl: 'purchaseUrl',
      imageUrl:
        'https://m.wellfeed.co.kr/web/product/big/202305/d535183f626c65184ac0d674477f744b.jpg',
      name: '맛난 사료',
    },
    {
      id: 2,
      purchaseUrl: 'purchaseUrl',
      imageUrl:
        'https://m.wellfeed.co.kr/web/product/big/202305/d535183f626c65184ac0d674477f744b.jpg',
      name: '맛난 사료',
    },
    {
      id: 3,
      purchaseUrl: 'purchaseUrl',
      imageUrl:
        'https://m.wellfeed.co.kr/web/product/big/202305/d535183f626c65184ac0d674477f744b.jpg',
      name: '맛난 사료',
    },
    {
      id: 4,
      purchaseUrl: 'purchaseUrl',
      imageUrl:
        'https://m.wellfeed.co.kr/web/product/big/202305/d535183f626c65184ac0d674477f744b.jpg',
      name: '맛난 사료',
    },
    {
      id: 5,
      purchaseUrl: 'purchaseUrl',
      imageUrl:
        'https://m.wellfeed.co.kr/web/product/big/202305/d535183f626c65184ac0d674477f744b.jpg',
      name: '맛난 사료',
    },
  ],
});

const getFoodDetail = (): GetFoodDetailRes => ({
  id: 0,
  name: '개꿀맛사료',
  imageUrl: 'https://m.wellfeed.co.kr/web/product/big/202305/d535183f626c65184ac0d674477f744b.jpg',
  purchaseUrl: '',
  rating: 4.12,
  reviewCount: 12,
  proteinSource: {
    primary: ['닭고기'],
    secondary: ['맛돌이'],
  },
  hasStandard: {
    us: true,
    eu: false,
  },
  functionality: ['튼튼건강해짐', '다이어트'],
  brand: {
    name: '오리젠',
    imageUrl:
      'https://www.doowonpet.co.kr/wp/wp-content/uploads/2022/08/%EC%98%A4%EB%A6%AC%EC%A0%A0-tm%EB%A1%9C%EA%B3%A0-ex2-4.jpg',
    state: '미국',
    foundedYear: 1918,
    hasResearchCenter: true,
    hasResidentVet: true,
  },
});

const foodFixture = { getFoodList, getFoodDetail };
export default foodFixture;
