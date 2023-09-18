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
    if (!checked) {
      if (!petProfile) return;

      const newQueryStringObj = generateQueryString({
        ...queryStringObj,
        ageGroups: petProfile.ageGroupId,
        breeds: petProfile.breedId,
        custom: 'on',
      });

      replaceQueryString(newQueryStringObj, { exclude: [] });

      return;
    }

    replaceQueryString('', { exclude: ['sortBy'] });
  };

  useEffect(() => {
    setChecked(isSimilarToMyPet);
  }, [Object.values(queryStringObj).join('')]);

  return { checked, onClickCustomReviewButton };
};
