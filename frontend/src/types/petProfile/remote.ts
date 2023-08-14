import { PetProfile } from './client';

interface PostPetProfileReq extends Omit<PetProfile, 'id' | 'imageUrl'> {
  imageFile?: File;
}

export type { PostPetProfileReq };
