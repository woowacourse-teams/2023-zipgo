declare module 'axios' {
  interface AxiosInterceptorManager<V> {
    handlers: unknown[];
  }
}

export {};
