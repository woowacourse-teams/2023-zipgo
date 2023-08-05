import { KEYWORD_EN, KEYWORDS_KO } from '@/constants/food';
import { KeywordEn, KeywordKo } from '@/types/food/client';

interface FromKoToEn {
  keyword: KeywordKo;
  from?: 'ko';
  to?: 'en';
}

interface FromEnToKo {
  keyword: KeywordEn;
  from?: 'en';
  to?: 'ko';
}

export const translateKeyword = ({
  keyword: targetKeyword,
  from = 'en',
  to = 'ko',
}: FromKoToEn | FromEnToKo) => {
  if (from === 'ko') {
    const index = KEYWORDS_KO.findIndex(keyword => keyword === targetKeyword);

    return KEYWORD_EN[index];
  }

  const index = KEYWORD_EN.findIndex(keyword => keyword === targetKeyword);

  return KEYWORDS_KO[index];
};
