/* eslint-disable max-classes-per-file */
import { Component, ErrorInfo, PropsWithChildren, ReactNode } from 'react';

import { IGNORE_KEY } from '@/constants/errors';
import { ErrorBoundaryState, ErrorBoundaryValue } from '@/types/common/errorBoundary';
import { RenderProps } from '@/types/common/utility';

const initialState: ErrorBoundaryState = {
  hasError: false,
  error: null,
};

interface ErrorBoundaryProps {
  fallback?: ReactNode | RenderProps<ErrorBoundaryValue>;
  ignore?: <E extends Error>(props: E) => boolean;
  onReset?: VoidFunction;
  onError?(error: Error, errorInfo: ErrorInfo): void;
}

class ErrorBoundary extends Component<PropsWithChildren<ErrorBoundaryProps>, ErrorBoundaryState> {
  constructor(props: ErrorBoundaryProps) {
    super(props);
    this.state = initialState;

    this.reset = this.reset.bind(this);
  }

  static getDerivedStateFromError(error: Error) {
    if (shouldIgnore(error)) throw error;

    return {
      hasError: true,
      error,
    };
  }

  componentDidCatch(error: Error, errorInfo: ErrorInfo): void {
    const { onError, ignore } = this.props;

    if (ignore?.(error)) {
      throw error;
    }

    onError?.(error, errorInfo);
  }

  reset() {
    const { onReset } = this.props;

    onReset?.();

    this.setState(initialState);
  }

  render() {
    const { error, hasError } = this.state;

    const { children, fallback } = this.props;

    if (!hasError) return children;

    if (!fallback) return null;

    if (typeof fallback === 'function') return fallback({ reset: this.reset, error });

    return fallback;
  }
}

class EndOfErrorBoundary extends ErrorBoundary {
  static getDerivedStateFromError(error: Error) {
    return {
      hasError: true,
      error,
    };
  }
}

const shouldIgnore = (error: Error, ignoreKey = IGNORE_KEY) =>
  Object.prototype.hasOwnProperty.call(error, ignoreKey);

export { EndOfErrorBoundary, ErrorBoundary };
