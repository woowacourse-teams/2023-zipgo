interface User {
  profileImgUrl: string;
  name: string;
}

interface PetProfile {
  name: string;
  age: number;
  breed: string;
  gender: string;
  weight: number;
  imageUrl: string;
  id: number;
}

export type { PetProfile, User };
