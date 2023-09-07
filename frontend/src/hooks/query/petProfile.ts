import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import { deletePet, getBreeds, getPet, getPets, postPetProfile, putPet } from '@/apis/petProfile';
import { MIXED_BREED, PET_SIZES } from '@/constants/petProfile';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { Parameter } from '@/types/common/utility';
import { PetProfile } from '@/types/petProfile/client';
import { PostPetProfileReq, PostPetProfileRes } from '@/types/petProfile/remote';
import { zipgoLocalStorage } from '@/utils/localStorage';

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
  const { updatePetProfile: updatePetProfileInHeader } = usePetProfile();
  const { mutate: addPetProfile, ...addPetProfileRestMutation } = useMutation<
    PostPetProfileRes,
    unknown,
    PostPetProfileReq,
    unknown
  >({
    mutationFn: postPetProfile,
    onSuccess: (postPetProfileRes, petProfile, context) => {
      const userInfo = zipgoLocalStorage.getUserInfo({ required: true });

      const petProfileWithId = {
        ...petProfile,
        id: 1,
        petSize: petProfile.breed === MIXED_BREED ? petProfile.petSize : PET_SIZES[0],
      } as PetProfile;

      updatePetProfileInHeader(petProfileWithId);

      zipgoLocalStorage.setUserInfo({ ...userInfo, hasPet: true });

      alert('반려동물 정보 등록이 완료되었습니다.');
    },
    onError: () => {
      alert('반려동물 정보 등록에 실패했습니다.');
    },
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
