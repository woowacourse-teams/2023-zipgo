import { Component, ReactNode } from 'react';

import { ErrorBoundaryState, ErrorBoundaryValue } from '@/types/common/errorBoundary';

interface ErrorBoundaryProps {
  fallback?: ReactNode | ((props: ErrorBoundaryValue) => ReactNode);
  children: ReactNode;
  onReset?: VoidFunction;
}

const initialState: ErrorBoundaryState = {
  hasError: false,
  error: null,
};

class ErrorBoundary extends Component<ErrorBoundaryProps, ErrorBoundaryState> {
  constructor(props: ErrorBoundaryProps) {
    super(props);
    this.state = initialState;

    this.reset = this.reset.bind(this);
  }

  static getDerivedStateFromError(error: Error): ErrorBoundaryState {
    return {
      hasError: true,
      error,
    };
  }

  reset() {
    const { onReset } = this.props;

    onReset?.();
    this.setState(initialState);
  }

  render() {
    const { hasError } = this.state;

    const { children, fallback } = this.props;

    if (!hasError) return children;

    if (!fallback) return null;

    if (typeof fallback === 'function') return fallback({ reset: this.reset });

    return fallback;
  }
}

export default ErrorBoundary;
