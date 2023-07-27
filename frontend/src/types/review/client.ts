import { ADVERSE_REACTIONS, STOOL_CONDITIONS, TASTE_PREFERENCES } from '@/constants/review';

type TastePreference = (typeof TASTE_PREFERENCES)[number];
type StoolCondition = (typeof STOOL_CONDITIONS)[number];
type AdverseReaction = (typeof ADVERSE_REACTIONS)[number];

export type Review = {
  id: number;
  profileIimageUrl?: string;
  reviewerName: string;
  rating: number;
  date: string;
  comment: string;
  tastePreference: TastePreference;
  stoolCondition: StoolCondition;
  adverseReactions: AdverseReaction[];
};
