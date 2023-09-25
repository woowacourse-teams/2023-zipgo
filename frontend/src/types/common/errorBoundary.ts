export interface ErrorBoundaryValue {
  reset: VoidFunction;
  error: Error;
}

export type ErrorBoundaryState =
  | {
      hasError: true;
      error: Error;
    }
  | {
      hasError: false;
      error: null;
    };
