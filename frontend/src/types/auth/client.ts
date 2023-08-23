import { PetProfile } from '../petProfile/client';

interface Tokens {
  accessToken: string;
}

interface User {
  id: number;
  name: string;
  email: string;
  profileImgUrl: string;
  hasPet: boolean;
  pets: PetProfile[];
}

export type { Tokens, User };
