import { ReactNode } from 'react';

type RenderProps<P extends object = object> = (payload: P) => ReactNode;

type Unpack<T> = T extends (infer U)[] ? U : T extends Set<infer U> ? U : T;

type Parameter<T extends (arg: never) => unknown> = Parameters<T>[0];

type StyledProps<T> = { [K in keyof T as `$${string & K}`]: T[K] };

type Values<T extends object> = T[keyof T];

type BaseFunction = (...args: never[]) => unknown;

export type NonEmptyArray<T> = readonly [T, ...T[]];

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

type CamelToSnake<
  Str extends string,
  Acc extends string = '',
> = Str extends `${infer Char}${infer Rest}` ? CamelToSnake<Rest, `${Acc}${Replace<Char>}`> : Acc;

export type { BaseFunction, CamelToSnake, Parameter, RenderProps, StyledProps, Unpack, Values };
