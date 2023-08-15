import { useState } from 'react';
import { styled } from 'styled-components';

import { usePetListQuery } from '@/hooks/query/petProfile';

import PetItem from './PetItem';

const PetList = () => {
  const { petList } = usePetListQuery();
  const [selectedPetId, setSelectedPetId] = useState(petList?.at(0)?.id);

  return (
    <PetListLayout role="radiogroup">
      {petList?.map(pet => (
        <PetItem
          key={pet.id}
          petInfo={pet}
          selected={pet.id === selectedPetId}
          onClick={() => setSelectedPetId(pet.id)}
        />
      ))}
    </PetListLayout>
  );
};

export default PetList;

const PetListLayout = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
`;
