import PetProfileProvider from '@/context/petProfile/PetProfileContext';

import PetProfileImageAdditionContent from './PetProfileImageAdditionContent';

const PetProfileImageAddition = () => (
  <PetProfileProvider>
    <PetProfileImageAdditionContent />
  </PetProfileProvider>
);

export default PetProfileImageAddition;
