import { useQuery } from '@tanstack/react-query';

import { getBreeds } from '@/apis/petProfile';

const QUERY_KEY = { breedList: 'breedList' };

export const useBreedListQuery = () => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.breedList],
    queryFn: () => getBreeds(),
  });

  return {
    breedList: data?.breeds,
    ...restQuery,
  };
};
