import { useParams } from 'react-router-dom';

type Params<Key extends string = string> = {
  readonly [key in Key]: string;
};

export const useValidParams = (payload: string[]): Readonly<Params<string>> => {
  const params = useParams();
  const validParams = payload.reduce((acc, key) => {
    if (key in params) {
      if (params[key] === undefined) throw new Error('Invaild Params');

      return { ...acc, [key]: params[key] };
    }

    throw new Error(`Param '${key}' not found`);
  }, {});

  return validParams;
};
