import {
  ADVERSE_REACTIONS,
  REVIEW_SUMMARY_KEYWORDS,
  STOOL_CONDITIONS,
  TASTE_PREFERENCES,
} from '@/constants/review';

import { Values } from '../common/utility';

type TastePreference = (typeof TASTE_PREFERENCES)[number];
type StoolCondition = (typeof STOOL_CONDITIONS)[number];
type AdverseReaction = (typeof ADVERSE_REACTIONS)[number];

type SummaryKeywordsEn = keyof typeof REVIEW_SUMMARY_KEYWORDS;

type SummaryKeywordsKo = Values<typeof REVIEW_SUMMARY_KEYWORDS>;

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
};

type ChartInfo = {
  name: string;
  percentage: number;
}[];

export type {
  AdverseReaction,
  AlignControlsMeta,
  ChartInfo,
  FilterControlsMeta,
  Review,
  StoolCondition,
  SummaryKeywordsEn,
  SummaryKeywordsKo,
  TastePreference,
};
