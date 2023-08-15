import { GetBreedsRes } from '@/types/petProfile/remote';

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

const petProfileFixture = { getBreeds };

export default petProfileFixture;
