import { createContext, PropsWithChildren, useContext, useMemo, useState } from 'react';

import { PostPetReq } from '@/types/petProfile/remote';

export interface PetProfileValue extends Omit<PostPetReq, 'weight'> {
  weight: string;
}

interface PetAdditionContext {
  petProfile: PetProfileValue;
  updatePetProfile: (newProfile: Partial<PetProfileValue>) => void;
}

const initialPetProfile: PetProfileValue = {
  name: '',
  age: -1,
  breed: '',
  gender: '남',
  weight: '',
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
