import { useParams } from 'react-router-dom';

type Params<Key extends string = string> = {
  readonly [key in Key]: string;
};

export const useValidParams = <T extends string>(paramKeys: T[]): Readonly<Params<T>> => {
  const params = useParams();
  const validParams = paramKeys.reduce((acc, key) => {
    if (!(key in params)) throw new Error(`Param '${key}' not found`);
    if (!params[key]) throw new Error('Invalid Params');

    return { ...acc, [key]: params[key] };
  }, {} as Readonly<Params<T>>);

  return validParams;
};
