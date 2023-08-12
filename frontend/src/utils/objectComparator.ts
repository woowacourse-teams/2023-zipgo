type ObjectWithStringValues<T extends string> = Record<T, string>;

export const isEqualObjectWithStringValues = <T extends string>(
  oldObj: Partial<ObjectWithStringValues<T>>,
  newObj: Partial<ObjectWithStringValues<T>>,
) => {
  const oldKeys = Object.keys(oldObj) as T[];
  const newKeys = Object.keys(newObj) as T[];

  if (oldKeys.length !== newKeys.length) return false;

  return oldKeys.every(key => oldObj[key] === newObj[key]);
};
