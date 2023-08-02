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
  purchaseUrl:
    'https://www.coupang.com/vp/products/6080929259?itemId=12642998513&vendorItemId=4116327396&q=%EB%82%98%EC%9A%B0%ED%94%84%EB%A0%88%EC%8B%9C+%EA%B0%95%EC%95%84%EC%A7%80%EC%9A%A9+%EC%82%AC%EB%A3%8C&itemsCount=36&searchId=2d6ab623c99242e388be97d7302d6cfe&rank=1&isAddedCart=',
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
