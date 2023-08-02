export type Parameter<T extends (arg: never) => unknown> = T extends (arg: infer U) => unknown
  ? U
  : never;

export type StyledProps<T> = { [K in keyof T as `$${string & K}`]: T[K] };
