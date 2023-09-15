declare const tag: unique symbol;

declare type InvariantOf<T> = T & InvariantBrand<T>;

declare interface InvariantBrand<T> {
  readonly [tag]: (args: T) => T;
}
