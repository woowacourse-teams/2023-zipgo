export interface ErrorBoundaryValue {
  reset: VoidFunction;
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
