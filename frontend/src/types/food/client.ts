export type Food = {
  id: number;
  brandName: string;
  foodName: string;
  imageUrl: string;
  purchaseUrl: string;
};

export type FoodDetail = Food & {
  name: string;
  rating: number;
  reviewCount: number;
  primaryIngredients: string[];
  hasStandard: {
    us: boolean;
    eu: boolean;
  };
  functionality: string[];
  brand: Brand;
};

export type Brand = {
  name: string;
  imageUrl: string;
  state: string;
  foundedYear: number;
  hasResearchCenter: boolean;
  hasResidentVet: boolean;
};

export type LastPetFoodId = 'lastPetFoodId';

export type Size = 'size';

export type NutritionStandards = 'nutritionStandards';

export type MainIngredients = 'mainIngredients';

export type Brands = 'brands';

export type Functionality = 'functionalities';

export type KeywordsForPaging = [LastPetFoodId, Size];

export type KeywordForPaging = KeywordsForPaging[number];

export type KeywordsKo = ['영양기준', '주원료', '브랜드', '기능성'];

export type KeywordsEn = [NutritionStandards, MainIngredients, Brands, Functionality];

export type KeywordKo = KeywordsKo[number];

export type KeywordEn = KeywordsEn[number];

export type Filters = {
  nutritionStandards: NutritionStandardsFilter[];
  mainIngredients: MainIngredientsFilter[];
  brands: BrandFilter[];
  functionalities: FunctionalityFilter[];
};

export type NutritionStandardsFilter = {
  id: number;
  nation: string;
};

export type MainIngredientsFilter = {
  id: number;
  ingredients: string;
};

export type BrandFilter = {
  id: number;
  brandUrl: string;
  brandName: string;
};

export type FunctionalityFilter = {
  id: number;
  functionality: string;
};
