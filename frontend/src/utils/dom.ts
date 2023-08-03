interface EventHandler<E> {
  (e: E): void;
}
interface ComposeEventHandlers {
  <E>(externalEventHandler?: EventHandler<E>, innerEventHandler?: EventHandler<E>): EventHandler<E>;
}

export const composeEventHandlers: ComposeEventHandlers =
  (externalEventHandler, innerEventHandler) => event => {
    externalEventHandler?.(event);
    innerEventHandler?.(event);
  };

export const preventScroll = (opened: boolean) => {
  document.body.style.overflow = opened ? 'hidden' : 'auto';
};
