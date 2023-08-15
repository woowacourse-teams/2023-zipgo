import { useMutation, useQuery } from '@tanstack/react-query';

import { deletePet, getBreeds, getPet, getPets, postPetProfile } from '@/apis/petProfile';
import { Parameter } from '@/types/common/utility';

const QUERY_KEY = { breedList: 'breedList', petItem: 'petItem', petList: 'petList' };

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

export const usePetItemQuery = (payload: Parameter<typeof getPet>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.petItem],
    queryFn: () => getPet(payload),
  });

  return {
    petItem: data,
    ...restQuery,
  };
};

export const usePetListQuery = () => {
  const { data, ...restQuery } = useQuery({
    queryKey: [QUERY_KEY.petList],
    queryFn: () => getPets(),
  });

  return {
    petList: data?.pets,
    ...restQuery,
  };
};

export const useAddPetProfileMutation = () => {
  const { mutateAsync: addPetProfile, ...addPetProfileRestMutation } = useMutation({
    mutationFn: postPetProfile,
  });

  return { addPetProfileMutation: { addPetProfile, ...addPetProfileRestMutation } };
};

export const useRemovePetMutation = () => {
  const { mutateAsync: removePet, ...removePetRestMutation } = useMutation({
    mutationFn: deletePet,
  });

  return { removePetMutation: { removePet, ...removePetRestMutation } };
};
