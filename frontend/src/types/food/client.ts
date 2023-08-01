export type Food = {
  id: number;
  name: string;
  imageUrl: string;
  purchaseUrl: string;
};

export type FoodDetail = Food & {
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
