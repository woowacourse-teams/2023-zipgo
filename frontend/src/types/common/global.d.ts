declare global {
  export interface ObjectConstructor {
    hasOwn(o: object, p: PropertyKey): p is keyof typeof o;
  }
}

export {};
