import PetProfileProvider from '@/context/petProfile/PetProfileContext';

import PetProfileEditionContent from './PetProfileEditionContent';

const PetProfileEdition = () => (
  <PetProfileProvider>
    <PetProfileEditionContent />
  </PetProfileProvider>
);

export default PetProfileEdition;
