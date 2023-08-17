import { createContext, ReactNode, useContext, useMemo, useState } from 'react';

import { PetProfile, PetSize } from '@/types/petProfile/client';

interface PetProfileContext {
  petProfile: PetProfileValue;
  updatePetProfile: (newProfile: Partial<PetProfileValue>) => void;
}

interface PetProfileValue extends Omit<PetProfile, 'id' | 'petSize'> {
  petSize?: PetSize;
}

const initialPetProfile: PetProfileValue = {
  name: '',
  age: 0,
  breed: '믹스견',
  gender: '남',
  weight: 1,
  imageUrl: '',
};

const PetProfileContext = createContext<PetProfileContext>({
  petProfile: initialPetProfile,
  updatePetProfile: () => {},
});

export const usePetProfileContext = () => useContext(PetProfileContext);

export const PetProfileProvider = ({ children }: { children: ReactNode }) => {
  const [petProfile, setPetProfile] = useState<PetProfileValue>(initialPetProfile);

  const updatePetProfile = (newProfile: Partial<PetProfileValue>) => {
    setPetProfile(prev => ({ ...prev, ...newProfile }));
  };

  const petProfileContextValue = useMemo(
    () => ({
      petProfile,
      updatePetProfile,
    }),
    [petProfile],
  );

  return (
    <PetProfileContext.Provider value={petProfileContextValue}>
      {children}
    </PetProfileContext.Provider>
  );
};
