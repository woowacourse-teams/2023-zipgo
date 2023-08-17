import { REVIEW_SUMMARY_KEYWORDS } from '@/constants/review';
import { SummaryKeywordsEn, SummaryKeywordsKo } from '@/types/review/client';

interface FromKoToEn {
  keyword: SummaryKeywordsKo;
  from?: 'ko';
  to?: 'en';
}

interface FromEnToKo {
  keyword: SummaryKeywordsEn;
  from?: 'en';
  to?: 'ko';
}

export const translateSummaryKeyword = ({
  keyword: targetKeyword,
  from = 'en',
  to = 'ko',
}: FromKoToEn | FromEnToKo) => {
  if (from === 'ko') {
    return Object.entries(REVIEW_SUMMARY_KEYWORDS).find(([en, ko]) => targetKeyword === ko)?.[0];
  }

  return Object.entries(REVIEW_SUMMARY_KEYWORDS).find(([en, ko]) => targetKeyword === en)?.[1];
};
