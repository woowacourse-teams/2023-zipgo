import { createContext, PropsWithChildren, useContext, useEffect, useMemo, useState } from 'react';

import { PetProfile } from '@/types/petProfile/client';
import { zipgoLocalStorage } from '@/utils/localStorage';

const PetProfileContext = createContext<PetProfileContext>({
  petProfile: zipgoLocalStorage.getPetProfile(),
  updatePetProfile() {},
  resetPetProfile() {},
  inScope: false,
});

PetProfileContext.displayName = 'PetProfile';

export const usePetProfile = () => useContext(PetProfileContext);

interface PetProfileContext {
  petProfile: PetProfile | null;
  updatePetProfile(petProfile: PetProfile): void;
  resetPetProfile: VoidFunction;
  inScope: boolean;
}

interface PetProfileProviderProps {}

const PetProfileProvider = (props: PropsWithChildren<PetProfileProviderProps>) => {
  const { children } = props;

  const [petProfile, setPetProfile] = useState<PetProfile | null>(null);

  const updatePetProfileLocalStorage = (petProfile: PetProfile) => {
    setPetProfile(petProfile);
    zipgoLocalStorage.setPetProfile(petProfile);
  };

  const resetPetProfileLocalStorage = () => {
    setPetProfile(null);
    zipgoLocalStorage.removePetProfile();
  };

  useEffect(() => {
    setPetProfile(zipgoLocalStorage.getPetProfile());
  }, []);

  const memoizedValue = useMemo(
    () => ({
      petProfile,
      updatePetProfile: updatePetProfileLocalStorage,
      resetPetProfile: resetPetProfileLocalStorage,
      inScope: true,
    }),
    [petProfile],
  );

  return <PetProfileContext.Provider value={memoizedValue}>{children}</PetProfileContext.Provider>;
};

export default PetProfileProvider;
