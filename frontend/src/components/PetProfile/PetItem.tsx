import { styled } from 'styled-components';

import EditIcon from '@/assets/svg/edit_icon.svg';
import FemaleIcon from '@/assets/svg/female_icon_dark.svg';
import MaleIcon from '@/assets/svg/male_icon_dark.svg';
import { MALE } from '@/constants/petProfile';
import { Gender, PetProfile } from '@/types/petProfile/client';

interface PetItemProps {
  petInfo: PetProfile;
  selected: boolean;
  onClick: VoidFunction;
}

const PetItem = (petItemProps: PetItemProps) => {
  const { petInfo, selected, onClick } = petItemProps;

  return (
    <PetItemContainer role="radio" selected={selected} onClick={onClick}>
      <PetItemContent>
        <PetImageAndDetail>
          <PetImageWrapper>
            <PetImage src={petInfo.imageUrl} alt={petInfo.name} />
          </PetImageWrapper>
          <div>
            <GenderAndName>
              <GenderImageWrapper>
                <GenderImage src={getGenderImage(petInfo.gender)} />
              </GenderImageWrapper>
              <PetName>{petInfo.name}</PetName>
            </GenderAndName>
            <BreedAndAge>{`${petInfo.breed} / ${getPetAge(petInfo.age)}`}</BreedAndAge>
          </div>
        </PetImageAndDetail>
      </PetItemContent>
      <EditButton type="button" aria-label="반려동물 정보 수정">
        <img src={EditIcon} alt="" />
      </EditButton>
    </PetItemContainer>
  );
};

export const getPetAge = (age: number) => {
  if (age === 0) return '1살 미만';
  if (age === 20) return '20살 이상';

  return `${age}살`;
};

export const getGenderImage = (gender: Gender) => {
  if (gender === MALE) return MaleIcon;

  return FemaleIcon;
};

export default PetItem;

const PetItemContainer = styled.li<{ selected: boolean }>`
  cursor: pointer;

  display: flex;
  gap: 1.6rem;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  padding: 1rem 2rem;

  list-style: none;

  background-color: ${({ selected }) => (selected ? '#D0E6F9' : 'transparent')};
  border-radius: 10px;
`;

const PetItemContent = styled.div`
  display: flex;
`;

const PetImageAndDetail = styled.div`
  display: flex;
  gap: 1.6rem;
  align-items: center;
`;

const PetName = styled.p`
  margin-top: 0.4rem;
  margin-left: 0.4rem;

  font-size: 2rem;
  font-weight: 700;
  line-height: 2rem;
  color: ${({ theme }) => theme.color.grey600};
  letter-spacing: -0.5px;
`;

const GenderAndName = styled.div`
  display: flex;
  align-items: center;

  margin-bottom: 0.8rem;
`;

const BreedAndAge = styled.p`
  font-size: 1.2rem;
  font-weight: 500;
  line-height: 1.2rem;
  color: ${({ theme }) => theme.color.grey400};
  letter-spacing: -0.5px;
`;

const PetImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 6rem;
  height: 6rem;

  background-color: ${({ theme }) => theme.color.white};
  border-radius: 50%;
`;

const PetImage = styled.img`
  position: absolute;
  top: 0;
  left: 0;

  width: 100%;
  height: 100%;

  object-fit: cover;
`;

const GenderImageWrapper = styled.div`
  position: relative;

  overflow: hidden;

  width: 2rem;
  height: 2rem;

  background-color: ${({ theme }) => theme.color.grey200};
  border-radius: 50%;
`;

const GenderImage = styled.img`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  width: 70%;
  height: 70%;

  object-fit: cover;
`;

const EditButton = styled.button`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 4.4rem;
  height: 3.2rem;

  font-size: 1.6rem;
  font-weight: 700;
  line-height: 2.4rem;
  color: ${({ theme }) => theme.color.white};
  letter-spacing: 0.02rem;

  background-color: ${({ theme }) => theme.color.white};
  border: 1px solid ${({ theme }) => theme.color.primary};
  border-radius: 20px;

  transition: all 100ms ease-in-out;

  &:active {
    scale: 0.98;
  }

  &:disabled {
    cursor: not-allowed;

    background-color: ${({ theme }) => theme.color.grey300};
  }
`;
