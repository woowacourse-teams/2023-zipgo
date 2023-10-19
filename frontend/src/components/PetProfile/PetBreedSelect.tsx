import { ComponentPropsWithoutRef } from 'react';
import { styled } from 'styled-components';

import { useBreedListQuery } from '@/hooks/query/petProfile';

interface PetBreedSelectProps extends ComponentPropsWithoutRef<'select'> {
  defaultBreed?: string;
}

const PetBreedSelect = (petBreedSelectProps: PetBreedSelectProps) => {
  const { defaultBreed, ...restProps } = petBreedSelectProps;
  const { breedList } = useBreedListQuery();

  return (
    <BreedSelect name="pet-breed" aria-label="견종 선택" {...restProps}>
      <option disabled selected={!defaultBreed}>
        여기를 눌러 아이의 견종을 선택해주세요.
      </option>
      {breedList?.map(breed => (
        <option key={breed.name} value={breed.name} selected={defaultBreed === breed.name}>
          {breed.name}
        </option>
      ))}
    </BreedSelect>
  );
};

export default PetBreedSelect;

const BreedSelect = styled.select`
  cursor: pointer;

  width: 100%;
  padding: 1.2rem;

  font-size: 1.6rem;
  color: ${({ theme }) => theme.color.grey600};

  border: none;
  border-bottom: 1px solid ${({ theme }) => theme.color.grey400};
  outline: none;

  &:focus {
    border-color: #d0e6f9;
    border-width: 2px;
  }
`;
