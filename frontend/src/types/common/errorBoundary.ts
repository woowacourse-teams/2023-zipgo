export interface ErrorBoundaryValue<E extends Error = Error> {
  reset: VoidFunction;
  error: E;
}

export type ErrorBoundaryState<E extends Error = Error> =
  | {
      hasError: true;
      error: E;
    }
  | {
      hasError: false;
      error: null;
    };
