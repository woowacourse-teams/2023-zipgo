import { rest } from 'msw';

import { BASE_URL } from '@/apis';
import { Food } from '@/types/food/client';
import { GetFoodListRes } from '@/types/food/remote';

import foodFixture from '../fixtures/food';

const foodHandlers = [
  rest.get(`${BASE_URL}/pet-foods`, (req, res, ctx) => {
    const lastPetFoodId = Number(req.url.searchParams.get('lastPetFoodId')) ?? 0;
    const size = Number(req.url.searchParams.get('size'));
    const brands = req.url.searchParams
      .getAll('brands')
      .map(rawBrand => decodeURIComponent(rawBrand));
    const foodList = foodFixture.getFoodList();

    let petFoods = [] as Food[];

    const filteredPetFoods = foodList.petFoods.filter(food => brands.includes(food.brandName));

    if (filteredPetFoods.length === 0) {
      petFoods = foodList.petFoods.slice(lastPetFoodId, lastPetFoodId + size);
    } else {
      petFoods = filteredPetFoods;
    }

    const responseData = { ...foodList, petFoods } as GetFoodListRes;

    return res(ctx.status(200), ctx.json(responseData));
  }),

  rest.get(`${BASE_URL}/pet-foods/filters`, (req, res, ctx) => {
    const foodListFilterMeta = foodFixture.getFoodListFilterMeta();

    return res(ctx.status(200), ctx.json(foodListFilterMeta));
  }),

  rest.get(`${BASE_URL}/pet-foods/:petFoodId`, (req, res, ctx) => {
    const foodDetail = foodFixture.getFoodDetail();

    return res(ctx.status(200), ctx.json(foodDetail));
  }),
];

export default foodHandlers;
