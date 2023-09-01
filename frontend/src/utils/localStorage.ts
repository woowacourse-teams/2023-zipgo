import { User } from '@/types/auth/client';
import { PetProfile } from '@/types/petProfile/client';

import { invariantOf } from './invariantOf';

interface LocalStorageKit {
  <T>(key: STORAGE_KEY): InvariantOf<KitComponents<T>>;
}

interface KitComponents<T> {
  getItem: GetLocalStorageItem<T>;
  setItem: SetLocalStorageItem<T>;
  removeItem: RemoveLocalStorageItem;
}

interface GetLocalStorageItem<T> {
  <R extends boolean>(options?: GetItemOptions<T, R>): ReturnType<typeof getItem<T, R>>;
}

interface SetLocalStorageItem<T> {
  (item: T): ReturnType<typeof setItem<T>>;
}

interface RemoveLocalStorageItem {
  (): ReturnType<typeof removeItem>;
}

type GetItemOptions<T, R extends boolean> = {
  defaultValue?: T | null;
  required?: R;
};

type GetItemResult<T, R extends boolean> = R extends true ? T : T | null;

type Tokens = {
  accessToken: string;
};

type STORAGE_KEY = keyof typeof STORAGE_KEY;

export const STORAGE_KEY = {
  tokens: 'tokens',
  userInfo: 'userInfo',
  petProfile: 'petProfile',
} as const;

export const localStorageKit: LocalStorageKit = <T>(key: STORAGE_KEY) =>
  invariantOf({
    getItem: <R extends boolean>(options?: GetItemOptions<T, R>) => getItem<T, R>(key, options),
    setItem: (item: T) => setItem(key, item),
    removeItem: () => removeItem(key),
  });

const getItem = <T = unknown, R extends boolean = false>(
  key: STORAGE_KEY,
  options?: GetItemOptions<T, R>,
): GetItemResult<T, R> => {
  const value = localStorage.getItem(key);
  const { defaultValue = null, required } = options || { defaultValue: null };

  if (required && !value) {
    throw new Error('Item does not exist in local storage.');
  }

  return value ? JSON.parse(value) : defaultValue;
};

const setItem = <T = unknown>(key: STORAGE_KEY, item: T) => {
  try {
    const stringifiedItem = JSON.stringify(item);

    localStorage.setItem(key, stringifiedItem);
  } catch (error) {
    throw new Error('Fail to set item. Enable the localStorage or check localStorage quota.');
  }
};

const removeItem = (key: STORAGE_KEY) => localStorage.removeItem(key);

const clearAuth = () => {
  localStorageKit(STORAGE_KEY.tokens).removeItem();
  localStorageKit(STORAGE_KEY.userInfo).removeItem();
  localStorageKit(STORAGE_KEY.petProfile).removeItem();
};

const getTokens = localStorageKit<Tokens>(STORAGE_KEY.tokens).getItem;

const setTokens = localStorageKit<Tokens>(STORAGE_KEY.tokens).setItem;

const getUserInfo = localStorageKit<User>(STORAGE_KEY.userInfo).getItem;

const setUserInfo = localStorageKit<User>(STORAGE_KEY.userInfo).setItem;

const getPetProfile = localStorageKit<PetProfile>(STORAGE_KEY.petProfile).getItem;

const setPetProfile = localStorageKit<PetProfile>(STORAGE_KEY.petProfile).setItem;

const removePetProfile = localStorageKit<PetProfile>(STORAGE_KEY.petProfile).removeItem;

export const zipgoLocalStorage = {
  clearAuth,
  getTokens,
  setTokens,
  getUserInfo,
  setUserInfo,
  getPetProfile,
  setPetProfile,
  removePetProfile,
};
