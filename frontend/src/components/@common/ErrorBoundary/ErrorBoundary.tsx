import { Component, ErrorInfo, PropsWithChildren, ReactNode } from 'react';
import { useLocation } from 'react-router-dom';

import { ErrorBoundaryState, ErrorBoundaryValue } from '@/types/common/errorBoundary';
import { RenderProps } from '@/types/common/utility';
import { resolveFunctionOrPrimitive, resolveRenderProps } from '@/utils/compound';
import { isIgnored, ZipgoError } from '@/utils/errors';
import { isDifferentArray } from '@/utils/isDifferentArray';

const initialState = {
  hasError: false,
  error: null,
} as const;

interface ErrorBoundaryProps<E extends Error> {
  mustCatch?: boolean;
  resetKeys?: unknown[];
  fallback?: ReactNode | RenderProps<ErrorBoundaryValue<E>>;
  shouldIgnore?: boolean | ((payload: { error: E }) => boolean);
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

  componentDidCatch(_: E, errorInfo: ErrorInfo): void {
    const { onError, shouldIgnore, mustCatch } = this.props;

    const { error, hasError } = this.state;

    if (!hasError) return;

    const willIgnore = isIgnored(error) || resolveFunctionOrPrimitive(shouldIgnore, { error });

    if (!mustCatch && willIgnore) throw error;

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

    return resolveRenderProps(fallback, { reset: this.reset, error });
  }
}

const ErrorBoundary = <E extends Error>(props: PropsWithChildren<ErrorBoundaryProps<E>>) => {
  const location = useLocation();

  const resetKeys = [location.key, ...(props.resetKeys || [])];

  return <BaseErrorBoundary<E> {...props} resetKeys={resetKeys} />;
};

const CriticalBoundary = <E extends Error>(props: PropsWithChildren<ErrorBoundaryProps<E>>) => (
  <ErrorBoundary<E> {...props} mustCatch />
);

export { CriticalBoundary, ErrorBoundary };
