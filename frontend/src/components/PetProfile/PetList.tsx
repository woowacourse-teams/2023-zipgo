import { Link } from 'react-router-dom';
import { styled } from 'styled-components';

import AddPetButtonIcon from '@/assets/svg/add_pet_button.svg';
import { usePetProfile } from '@/context/petProfile/PetProfileContext';
import { usePetListQuery } from '@/hooks/query/petProfile';
import { PATH } from '@/router/routes';

import PetItem from './PetItem';

const PetList = () => {
  const { petList } = usePetListQuery();
  const { petProfile, updatePetProfile } = usePetProfile();

  return (
    <PetListLayout role="radiogroup">
      {petList?.map(pet => (
        <PetItem
          key={pet.id}
          petInfo={pet}
          selected={pet.id === petProfile?.id || (petList.length === 1 && petProfile !== null)}
          onClick={() => updatePetProfile(pet)}
        />
      ))}
      {Boolean(!petList?.length) && <AddPetButton />}
    </PetListLayout>
  );
};

export default PetList;

const AddPetButton = () => (
  <AddButtonWrapper to={PATH.PET_PROFILE_ADDITION}>
    <img src={AddPetButtonIcon} alt="반려견 추가하기" />
    <AddButtonText>반려견 등록하기</AddButtonText>
  </AddButtonWrapper>
);

const PetListLayout = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
`;

const AddButtonWrapper = styled(Link)`
  display: flex;
  gap: 1.2rem;
  align-items: center;

  padding: 1rem 2rem;

  text-decoration: none;

  background-color: transparent;
  border: none;
`;

const AddButtonText = styled.p`
  margin-top: 0.2rem;

  font-size: 1.8rem;
  font-weight: 700;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.05rem;
`;
