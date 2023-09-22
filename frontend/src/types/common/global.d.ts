declare global {
  export interface ObjectConstructor {
    keys<T extends object>(o: InvariantOf<T>): Array<keyof T>;
  }
}

export {};
