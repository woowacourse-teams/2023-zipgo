export type Parameter<T extends (arg: never) => unknown> = Parameters<T>[0];
