declare global {
  export interface ObjectConstructor {
    hasOwn<T extends object>(o: T, p: PropertyKey): p is keyof typeof o;
  }
}

export {};
