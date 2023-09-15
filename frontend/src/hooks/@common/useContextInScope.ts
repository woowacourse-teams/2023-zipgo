import { Context, useContext } from 'react';

type InScope<T = object> = { inScope: boolean } & T;

const useContextInScope: <T extends InScope>(context: Context<T>) => InScope<T> = context => {
  const value = useContext(context);

  if (!value.inScope) throw new Error(`Must use in ${context.displayName || 'root component'}`);

  return value;
};

export default useContextInScope;
