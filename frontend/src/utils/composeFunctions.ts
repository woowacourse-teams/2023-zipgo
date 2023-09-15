export const composeFunctions =
  (...fnList: (VoidFunction | undefined)[]) =>
  () =>
    fnList.forEach(fn => fn?.());
