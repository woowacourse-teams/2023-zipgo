import { GetFoodListRes } from '@/types/food/remote';

const getFoodList = (): GetFoodListRes => ({
  foodList: [
    {
      id: 1,
      purchaseUrl: 'purchaseUrl',
      imageUrl: 'imageUrl',
      name: '맛난 사료',
    },
  ],
});

const foodFixture = { getFoodList };
export default foodFixture;
