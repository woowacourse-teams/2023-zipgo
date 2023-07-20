import { GetFoodListRes } from '@/types/food/remote';

const getFoodList = (): GetFoodListRes => ({
  foodList: [
    {
      id: 1,
      purchaseUrl: 'purchaseUrl',
      imageUrl: 'imageUrl',
      name: '맛난 사료',
    },
    {
      id: 2,
      purchaseUrl: 'purchaseUrl',
      imageUrl: 'imageUrl',
      name: '맛난 사료',
    },
    {
      id: 3,
      purchaseUrl: 'purchaseUrl',
      imageUrl: 'imageUrl',
      name: '맛난 사료',
    },
    {
      id: 4,
      purchaseUrl: 'purchaseUrl',
      imageUrl: 'imageUrl',
      name: '맛난 사료',
    },
    {
      id: 5,
      purchaseUrl: 'purchaseUrl',
      imageUrl: 'imageUrl',
      name: '맛난 사료',
    },
  ],
});

const foodFixture = { getFoodList };
export default foodFixture;
