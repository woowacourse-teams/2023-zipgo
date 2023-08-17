import { createContext, PropsWithChildren, useEffect, useMemo, useState } from 'react';

import useContextInScope from '@/hooks/@common/useContextInScope';
import { PetProfile } from '@/types/petProfile/client';

const getPetProfileLocalStorage = () => {
  const stringifyPetProfile = localStorage.getItem('petProfile');

  return stringifyPetProfile ? (JSON.parse(stringifyPetProfile) as PetProfile) : null;
};

const PetProfileContext = createContext<PetProfileContext>({
  petProfile: getPetProfileLocalStorage(),
  updatePetProfile() {},
  inScope: false,
});

PetProfileContext.displayName = 'PetProfile';

export const usePetProfile = () => useContextInScope(PetProfileContext);

interface PetProfileContext {
  petProfile: PetProfile | null;
  updatePetProfile(petProfile: PetProfile): void;
  inScope: boolean;
}

interface PetProfileProviderProps {}

const PetProfileProvider = (props: PropsWithChildren<PetProfileProviderProps>) => {
  const { children } = props;

  const [petProfile, setPetProfile] = useState<PetProfile | null>(null);

  const updatePetProfileLocalStorage = (petProfile: PetProfile) => {
    setPetProfile(petProfile);
    localStorage.setItem('petProfile', JSON.stringify(petProfile));
  };

  useEffect(() => {
    setPetProfile(getPetProfileLocalStorage());
  }, []);

  const memoizedValue = useMemo(
    () => ({
      petProfile,
      updatePetProfile: updatePetProfileLocalStorage,
      inScope: true,
    }),
    [petProfile],
  );

  return <PetProfileContext.Provider value={memoizedValue}>{children}</PetProfileContext.Provider>;
};

export default PetProfileProvider;
