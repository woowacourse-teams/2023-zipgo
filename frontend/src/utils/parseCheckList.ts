import { Unpack, Values } from '@/types/common/utility';

export const parseCheckList = <T extends Record<string, Set<number | string>>>(checkList: T) =>
  Object.entries(checkList).reduce(
    (acc, [keyword, value]) => ({ ...acc, [keyword]: Array.from(value) }),
    {},
  ) as Partial<Record<keyof T, Unpack<Values<T>>[]>>;
