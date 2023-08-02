export type Parameter<T extends (arg: never) => unknown> = Parameters<T>[0];

export type StyledProps<T> = { [K in keyof T as `$${string & K}`]: T[K] };
