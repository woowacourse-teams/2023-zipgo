import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';

import { deletePet, getBreeds, getPet, getPets, postPet, putPet } from '@/apis/petProfile';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { Parameter } from '@/types/common/utility';
import {
  DeletePetReq,
  DeletePetRes,
  PostPetReq,
  PostPetRes,
  PutPetReq,
  PutPetRes,
} from '@/types/petProfile/remote';
import { zipgoLocalStorage } from '@/utils/localStorage';

const PET_PROFILE_QUERY_KEY = {
  breedList: 'breedList',
  petItem: (petId: number) => ['petItem', petId],
  petList: 'petList',
};

export const useBreedListQuery = () => {
  const { data, ...restQuery } = useQuery({
    queryKey: [PET_PROFILE_QUERY_KEY.breedList],
    queryFn: () => getBreeds(),
  });

  return {
    breedList: data?.breeds,
    ...restQuery,
  };
};

export const usePetItemQuery = (payload: Parameter<typeof getPet>) => {
  const { data, ...restQuery } = useQuery({
    queryKey: PET_PROFILE_QUERY_KEY.petItem(payload.petId),
    queryFn: () => getPet(payload),
  });

  return {
    petItem: data,
    ...restQuery,
  };
};

export const usePetListQuery = () => {
  const { data, ...restQuery } = useQuery({
    queryKey: [PET_PROFILE_QUERY_KEY.petList],
    queryFn: () => getPets(),
  });

  return {
    petList: data?.pets,
    ...restQuery,
  };
};

export const useAddPetMutation = () => {
  const { refetch: refetchPetList } = usePetListQuery();
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

      zipgoLocalStorage.setUserInfo({ ...userInfo, hasPet: true });

      refetchPetList().then(({ data }) => {
        const newestPet = data?.pets.at(-1);

        if (newestPet) updatePetProfileInHeader(newestPet);
      });

      alert('반려동물 정보 등록이 완료되었습니다.');
    },
    onError: () => {
      alert('반려동물 정보 등록에 실패했습니다.');
    },
  });

  return { addPetMutation: { addPet, ...addPetRestMutation } };
};

export const useEditPetMutation = () => {
  const queryClient = useQueryClient();
  const { petProfile: petProfileInHeader, updatePetProfile: updatePetProfileInHeader } =
    usePetProfile();

  const { mutate: editPet, ...editPetRestMutation } = useMutation<
    PutPetRes,
    unknown,
    PutPetReq,
    unknown
  >({
    mutationFn: putPet,
    onSuccess: (putPetRes, newPetProfile, context) => {
      if (newPetProfile.id === petProfileInHeader?.id) updatePetProfileInHeader(newPetProfile);

      queryClient.invalidateQueries([PET_PROFILE_QUERY_KEY.petList]);
      queryClient.invalidateQueries(PET_PROFILE_QUERY_KEY.petItem(newPetProfile.id));

      alert('반려동물 정보 수정이 완료되었습니다.');
    },
    onError: () => {
      alert('반려동물 정보 수정에 실패했습니다.');
    },
  });

  return { editPetMutation: { editPet, ...editPetRestMutation } };
};

export const useRemovePetMutation = () => {
  const queryClient = useQueryClient();
  const { refetch: refetchPetList } = usePetListQuery();

  const {
    petProfile: petProfileInHeader,
    updatePetProfile: updatePetProfileInHeader,
    resetPetProfile: resetPetProfileInHeader,
  } = usePetProfile();

  const { mutate: removePet, ...removePetRestMutation } = useMutation<
    DeletePetRes,
    unknown,
    DeletePetReq,
    unknown
  >({
    mutationFn: deletePet,
    onSuccess: async (deletePetRes, deletePetReq, context) => {
      const { data } = await refetchPetList();
      const userInfo = zipgoLocalStorage.getUserInfo({ required: true });

      if (data?.pets.length === 0) zipgoLocalStorage.setUserInfo({ ...userInfo, hasPet: false });

      if (deletePetReq.petId === petProfileInHeader?.id) {
        const newestPet = data?.pets.at(-1);

        if (newestPet) updatePetProfileInHeader(newestPet);
        if (!newestPet) resetPetProfileInHeader();
      }

      queryClient.invalidateQueries([PET_PROFILE_QUERY_KEY.petList]);
      queryClient.invalidateQueries(PET_PROFILE_QUERY_KEY.petItem(deletePetReq.petId));

      alert('반려동물 정보를 삭제했습니다.');
    },
    onError: () => {
      alert('반려동물 정보 삭제에 실패했습니다.');
    },
  });

  return { removePetMutation: { removePet, ...removePetRestMutation } };
};
