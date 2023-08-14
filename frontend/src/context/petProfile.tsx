import { createContext, ReactNode, useContext, useMemo, useState } from 'react';

import { PetProfile } from '@/types/petProfile/client';

interface PetProfileContext {
  petProfile: PetProfileValue;
  updatePetProfile: (newProfile: Partial<PetProfileValue>) => void;
}

interface PetProfileValue extends Omit<PetProfile, 'id' | 'imageUrl'> {
  imageFile?: File;
}

const initialPetProfile: PetProfileValue = {
  name: '',
  age: 0,
  breeds: '믹스견',
  breedsSize: '소형견',
  gender: '남',
  weight: 1,
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
