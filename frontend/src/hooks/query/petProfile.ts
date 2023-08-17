import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import { deletePet, getBreeds, getPet, getPets, postPetProfile, putPet } from '@/apis/petProfile';
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
  const queryClient = useQueryClient();
  const resetPetItemQuery = () => queryClient.removeQueries([QUERY_KEY.petItem]);

  return {
    petItem: data,
    ...restQuery,
    resetPetItemQuery,
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

export const useEditPetMutation = () => {
  const { mutateAsync: editPet, ...editPetRestMutation } = useMutation({
    mutationFn: putPet,
  });

  return { editPetMutation: { editPet, ...editPetRestMutation } };
};

export const useRemovePetMutation = () => {
  const { mutateAsync: removePet, ...removePetRestMutation } = useMutation({
    mutationFn: deletePet,
  });

  return { removePetMutation: { removePet, ...removePetRestMutation } };
};
