import { GetBreedsRes, GetPetsRes } from '@/types/petProfile/remote';

const getBreeds = (): GetBreedsRes => ({
  breeds: [
    {
      id: 1,
      name: '믹스견',
    },
    {
      id: 2,
      name: '포메라니안',
    },
    {
      id: 3,
      name: '몰티즈',
    },
    {
      id: 4,
      name: '진돗개',
    },
    {
      id: 4,
      name: '골든리트리버',
    },
    {
      id: 5,
      name: '시베리안허스키',
    },
  ],
});

const getPets = (): GetPetsRes => ({
  pets: [
    {
      id: 1,
      name: '쫑이',
      age: 0,
      breed: '시베리안 허스키',
      breedId: 5,
      petSize: '중형견',
      gender: '남',
      weight: 2,
      imageUrl: 'https://avatars.githubusercontent.com/u/24777828?v=4',
      ageGroup: '퍼피',
      ageGroupId: 1,
    },
    {
      id: 2,
      name: '에디',
      age: 19,
      breed: '믹스견',
      breedId: 1,
      petSize: '소형견',
      gender: '여',
      weight: 100,
      imageUrl: 'https://avatars.githubusercontent.com/u/24777828?v=4',
      ageGroup: '시니어',
      ageGroupId: 3,
    },
  ],
});

const petProfileFixture = { getBreeds, getPets };

export default petProfileFixture;
