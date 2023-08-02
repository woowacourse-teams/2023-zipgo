import { LoginZipgoAuthRes } from '@/types/auth/remote';

const loginZipgoAuth = (): LoginZipgoAuthRes => ({
  accessToken: 'accessToken',
});

const authFixture = { loginZipgoAuth };

export default authFixture;
