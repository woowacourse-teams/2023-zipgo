interface Func<E> {
  (arg: E): void;
}

interface ComposeFunctions {
  <T>(externalFunction?: Func<T>, innerFunction?: Func<T>): Func<T>;
}

export const composeFunctions: ComposeFunctions = (externalFunction, innerFunction) => arg => {
  externalFunction?.(arg);
  innerFunction?.(arg);
};

export const preventScroll = (opened: boolean) => {
  document.body.style.overflow = opened ? 'hidden' : 'auto';
};
