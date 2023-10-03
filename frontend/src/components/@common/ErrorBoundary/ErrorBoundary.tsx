/* eslint-disable max-classes-per-file */
import { Component, ComponentProps, ErrorInfo, PropsWithChildren, ReactNode } from 'react';
import { useLocation } from 'react-router-dom';

import { ErrorBoundaryState, ErrorBoundaryValue } from '@/types/common/errorBoundary';
import { RenderProps } from '@/types/common/utility';
import { resolveRenderProps } from '@/utils/compound';
import { shouldIgnore, ZipgoError } from '@/utils/errors';
import { isDifferentArray } from '@/utils/isDifferentArray';

const initialState = {
  hasError: false,
  error: null,
} as const;

interface ErrorBoundaryProps<E extends Error> {
  resetKeys?: unknown[];
  fallback?: ReactNode | RenderProps<ErrorBoundaryValue<E>>;
  ignore?<E extends Error>(props: E): boolean;
  onError?(payload: { error: E; errorInfo: ErrorInfo }): void;
  onReset?: VoidFunction;
}

class BaseErrorBoundary<E extends Error = Error> extends Component<
  PropsWithChildren<ErrorBoundaryProps<E>>,
  ErrorBoundaryState<E>
> {
  constructor(props: PropsWithChildren<ErrorBoundaryProps<E>>) {
    super(props);
    this.state = initialState;

    this.reset = this.reset.bind(this);
  }

  static getDerivedStateFromError(error: Error) {
    if (shouldIgnore(error)) throw error;

    return {
      hasError: true,
      error: ZipgoError.convertToError(error),
    };
  }

  componentDidUpdate(prevProps: ErrorBoundaryProps<E>) {
    if (isDifferentArray(this.props.resetKeys, prevProps.resetKeys)) {
      this.reset();
    }
  }

  componentDidCatch(error: E, errorInfo: ErrorInfo): void {
    const { onError, ignore } = this.props;

    if (ignore?.(error)) {
      throw error;
    }

    onError?.({ error, errorInfo });
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

    return resolveRenderProps(fallback, { reset: this.reset, error });
  }
}

class BaseEndOfErrorBoundary<E extends Error = Error> extends BaseErrorBoundary<E> {
  static getDerivedStateFromError(error: Error) {
    return {
      hasError: true,
      error: ZipgoError.convertToError(error),
    };
  }
}

const resetOnNavigate = (ErrorBoundary: typeof BaseErrorBoundary) => {
  const ErrorBoundaryWithResetKeys = <E extends Error>(
    props: ComponentProps<typeof ErrorBoundary<E>>,
  ) => {
    const location = useLocation();

    const resetKeys = [location.key, ...(('resetKeys' in props && props.resetKeys) || [])];

    return <ErrorBoundary<E> {...props} resetKeys={resetKeys} />;
  };

  return ErrorBoundaryWithResetKeys;
};

const ErrorBoundary = resetOnNavigate(BaseErrorBoundary);

const EndOfErrorBoundary = resetOnNavigate(BaseEndOfErrorBoundary);

export { EndOfErrorBoundary, ErrorBoundary };
