export type Parameter<T extends (arg: never) => unknown> = Parameters<T>[0];

export type StyledProps<T> = { [K in keyof T as `$${string & K}`]: T[K] };

type Separator = '_';

type IsChar<Char extends string> = Uppercase<Char> extends Lowercase<Char> ? false : true;

type IsCapitalized<Char extends string> = IsChar<Char> extends true
  ? Uppercase<Char> extends Char
    ? true
    : false
  : false;

type Replace<Char extends string> = IsCapitalized<Char> extends true
  ? `${Separator}${Lowercase<Char>}`
  : Char;

export type CamelToSnake<
  Str extends string,
  Acc extends string = '',
> = Str extends `${infer Char}${infer Rest}` ? CamelToSnake<Rest, `${Acc}${Replace<Char>}`> : Acc;
