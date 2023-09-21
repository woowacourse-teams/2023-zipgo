import { getArrayMutationMethod } from './getArrayMutationMethod';

export const getValidQueries = <T extends string>(search: string, queryKeys: Readonly<T[]>) =>
  search
    .replace(/^\?/, '')
    .split('&')
    .reduce<Partial<Record<T, string>>>((queries, keyValue, _, search) => {
      const [key, value] = keyValue.split('=');
      const includes = getArrayMutationMethod(queryKeys, 'includes');

      if (Object.prototype.hasOwnProperty.call(queries, key)) {
        throw new Error('Duplicated query');
      }

      return includes(key) ? { ...queries, [key]: value } : queries;
    }, Object.create(Object.prototype));
