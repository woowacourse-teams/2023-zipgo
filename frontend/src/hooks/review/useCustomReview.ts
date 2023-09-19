import { useEffect, useState } from 'react';

import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { generateQueryString } from '@/router/routes';

import useEasyNavigate from '../@common/useEasyNavigate';
import useValidQueryString from '../common/useValidQueryString';

export const useCustomReview = () => {
  const { replaceQueryString } = useEasyNavigate();
  const queryStringObj = useValidQueryString(['ageGroups', 'breeds', 'sortBy']);
  const { petProfile } = usePetProfile();
  const [checked, setChecked] = useState(false);

  const isSimilarToMyPet =
    petProfile?.ageGroupId === Number(queryStringObj.ageGroups) &&
    petProfile?.breedId === Number(queryStringObj.breeds);

  const onClickCustomReviewButton = () => {
    if (!petProfile) {
      alert('반려동물 등록 후 이용 가능합니다.');

      return;
    }

    if (checked) replaceQueryString('', { exclude: ['sortBy'] });

    if (!checked) {
      const newQueryStringObj = generateQueryString({
        ...queryStringObj,
        ageGroups: petProfile.ageGroupId,
        breeds: petProfile.breedId,
        custom: 'on',
      });

      replaceQueryString(newQueryStringObj, { exclude: [] });
    }
  };

  useEffect(() => {
    setChecked(isSimilarToMyPet);
  }, [Object.values(queryStringObj).join()]);

  return { checked, onClickCustomReviewButton };
};
