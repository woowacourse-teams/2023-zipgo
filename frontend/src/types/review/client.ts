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

interface Review {
  id: number;
  writerId: number;
  rating: number;
  date: string;
  comment: string;
  tastePreference: TastePreference;
  stoolCondition: StoolCondition;
  adverseReactions: AdverseReaction[];
  petProfile: PetProfile;
  helpfulReaction: HelpfulReaction;
}

interface PetProfile {
  id: number;
  name: string;
  profileUrl: string;
  writtenAge: number;
  writtenWeight: number;
  breed: Breed;
}

interface Breed {
  id: number;
  name: string;
  size: Size;
}

interface Size {
  id: number;
  name: string;
}

interface HelpfulReaction {
  count: number;
  reacted: boolean;
}

type AlignControlsMeta = {
  id: number;
  name: string;
}[];

type FilterControlsMeta = {
  id: number;
  name: string;
}[];

type ChartInfo = {
  name: string;
  percentage: number;
}[];

export type {
  AdverseReaction,
  AlignControlsMeta,
  ChartInfo,
  FilterControlsMeta,
  PetProfile,
  Review,
  StoolCondition,
  SummaryKeywordsEn,
  SummaryKeywordsKo,
  TastePreference,
};
