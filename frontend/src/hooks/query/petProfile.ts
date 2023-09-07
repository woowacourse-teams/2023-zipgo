import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import { deletePet, getBreeds, getPet, getPets, postPet, putPet } from '@/apis/petProfile';
import { MIXED_BREED, PET_SIZES } from '@/constants/petProfile';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { Parameter } from '@/types/common/utility';
import { PetProfile } from '@/types/petProfile/client';
import {
  DeletePetReq,
  DeletePetRes,
  PostPetReq,
  PostPetRes,
  PutPetReq,
  PutPetRes,
} from '@/types/petProfile/remote';
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

export const useAddPetMutation = () => {
  const { updatePetProfile: updatePetProfileInHeader } = usePetProfile();
  const { mutate: addPet, ...addPetRestMutation } = useMutation<
    PostPetRes,
    unknown,
    PostPetReq,
    unknown
  >({
    mutationFn: postPet,
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

  return { addPetMutation: { addPet, ...addPetRestMutation } };
};

export const useEditPetMutation = () => {
  const { resetPetItemQuery } = usePetItemQuery({ petId: 0 });
  const { updatePetProfile: updatePetProfileInHeader } = usePetProfile();

  const { mutate: editPet, ...editPetRestMutation } = useMutation<
    PutPetRes,
    unknown,
    PutPetReq,
    unknown
  >({
    mutationFn: putPet,
    onSuccess: (putPetRes, newPetProfile, context) => {
      updatePetProfileInHeader(newPetProfile);
      resetPetItemQuery();

      zipgoLocalStorage.setPetProfile(newPetProfile);

      alert('반려동물 정보 수정이 완료되었습니다.');
    },
    onError: () => {
      alert('반려동물 정보 수정에 실패했습니다.');
    },
  });

  return { editPetMutation: { editPet, ...editPetRestMutation } };
};

export const useRemovePetMutation = () => {
  const { resetPetItemQuery } = usePetItemQuery({ petId: 0 });
  const { resetPetProfile: resetPetProfileInHeader } = usePetProfile();

  const { mutate: removePet, ...removePetRestMutation } = useMutation<
    DeletePetRes,
    unknown,
    DeletePetReq,
    unknown
  >({
    mutationFn: deletePet,
    onSuccess: (deletePetRes, deletePetReq, context) => {
      const userInfo = zipgoLocalStorage.getUserInfo({ required: true });

      zipgoLocalStorage.setUserInfo({ ...userInfo, hasPet: false });

      resetPetProfileInHeader();
      resetPetItemQuery();

      alert('반려동물 정보를 삭제했습니다.');
    },
    onError: () => {
      alert('반려동물 정보 삭제에 실패했습니다.');
    },
  });

  return { removePetMutation: { removePet, ...removePetRestMutation } };
};
