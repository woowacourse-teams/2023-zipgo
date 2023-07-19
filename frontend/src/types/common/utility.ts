export type Parameter<T extends (arg: never) => unknown> = T extends (arg: infer U) => unknown
  ? U
  : never;
