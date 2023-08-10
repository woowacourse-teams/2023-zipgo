export type PathPram = number | string;

export type PathParams<T extends string> = Record<T, PathPram>;

export type QueryStringProperty = {
  [key: QueryStringPropertyKey]: QueryStringPropertyValue;
};

export type QueryStringPropertyKey<T extends PropertyKey = PropertyKey> = T extends symbol
  ? never
  : T;

export type QueryStringPropertyValue = number | string | number[] | string[];
