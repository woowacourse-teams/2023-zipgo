import { ADVERSE_REACTIONS, STOOL_CONDITIONS, TASTE_PREFERENCES } from '@/constants/review';

type TastePreference = (typeof TASTE_PREFERENCES)[number];
type StoolCondition = (typeof STOOL_CONDITIONS)[number];
type AdverseReaction = (typeof ADVERSE_REACTIONS)[number];

type Review = {
  id: number;
  profileImageUrl?: string;
  reviewerName: string;
  rating: number;
  date: string;
  comment: string;
  tastePreference: TastePreference;
  stoolCondition: StoolCondition;
  adverseReactions: AdverseReaction[];
};

type AlignControlsMeta = {
  id: number;
  name: string;
}[];

type FilterControlsMeta = {
  id: number;
  name: string;
}[];

export type {
  AdverseReaction,
  AlignControlsMeta,
  FilterControlsMeta,
  Review,
  StoolCondition,
  TastePreference,
};
