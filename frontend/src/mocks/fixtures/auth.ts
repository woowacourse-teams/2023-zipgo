import { LoginZipgoAuthRes } from '@/types/auth/remote';

const loginZipgoAuth = (): LoginZipgoAuthRes => ({
  accessToken: 'accessToken',
  authResponse: {
    name: 'Zipgo',
    profileImgUrl: 'url',
    hasPet: false,
  },
});

const authFixture = { loginZipgoAuth };

export default authFixture;
