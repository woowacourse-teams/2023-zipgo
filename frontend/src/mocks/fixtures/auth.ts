import { LoginZipgoAuthRes } from '@/types/auth/remote';

const loginZipgoAuth = (): LoginZipgoAuthRes => ({
  accessToken: 'accessToken',
  authResponse: {
    id: 1,
    email: 'bebe@zipgo.pet',
    name: 'Zipgo',
    profileImgUrl: 'url',
    hasPet: true,
    pets: [
      {
        id: 71,
        name: '두식이',
        age: 3,
        breed: '믹스견',
        gender: '남',
        petSize: '대형견',
        imageUrl: 'https://image.zipgo.pet/prod/pet-image/ddf683ff-5e12-454d-b926-ebefa43c6767',
        weight: 11.1,
      },
    ],
  },
});

const authFixture = { loginZipgoAuth };

export default authFixture;
