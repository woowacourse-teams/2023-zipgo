import { keywordsEn, keywordsKo } from '@/constants/food';
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
    const index = keywordsKo.findIndex(keyword => keyword === targetKeyword);

    return keywordsEn[index];
  }

  const index = keywordsEn.findIndex(keyword => keyword === targetKeyword);

  return keywordsKo[index];
};
