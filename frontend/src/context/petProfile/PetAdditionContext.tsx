import { createContext, PropsWithChildren, useContext, useMemo, useState } from 'react';

import { PostPetReq } from '@/types/petProfile/remote';

export interface PetProfileValue extends PostPetReq {}

interface PetAdditionContext {
  petProfile: PetProfileValue;
  updatePetProfile: (newProfile: Partial<PetProfileValue>) => void;
}

const initialPetProfile: PetProfileValue = {
  name: '',
  age: 0,
  breed: '믹스견',
  gender: '남',
  weight: 1,
  imageUrl: '',
};

const PetAdditionContext = createContext<PetAdditionContext>({
  petProfile: initialPetProfile,
  updatePetProfile: () => {},
});

export const usePetAdditionContext = () => useContext(PetAdditionContext);

export const PetAdditionProvider = ({ children }: PropsWithChildren) => {
  const [petProfile, setPetProfile] = useState<PetProfileValue>(initialPetProfile);

  const updatePetProfile = (newProfile: Partial<PetProfileValue>) => {
    setPetProfile(prev => ({ ...prev, ...newProfile }));
  };

  const PetAdditionContextValue = useMemo(
    () => ({
      petProfile,
      updatePetProfile,
    }),
    [petProfile],
  );

  return (
    <PetAdditionContext.Provider value={PetAdditionContextValue}>
      {children}
    </PetAdditionContext.Provider>
  );
};
