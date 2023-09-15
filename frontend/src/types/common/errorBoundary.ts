export interface ErrorBoundaryValue {
  reset: VoidFunction;
}

export interface ErrorBoundaryState {
  hasError: boolean;
  error: Error | null;
}
