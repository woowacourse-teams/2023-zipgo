/* eslint-disable @typescript-eslint/no-explicit-any */
import {
  ComponentPropsWithoutRef,
  createContext,
  PropsWithChildren,
  RefObject,
  useCallback,
  useEffect,
  useMemo,
  useState,
} from 'react';

import { SelectProps, SelectPropsWithValue } from '@/components/@common/Select/Select';
import useBoolean from '@/hooks/@common/useBoolean';
import useContextInScope from '@/hooks/@common/useContextInScope';

interface SelectContext extends ComponentPropsWithoutRef<'div'> {
  isOpened: boolean;
  inScope: boolean;
  selectedValue: string;
  openHandler: VoidFunction;
  changeValue: (value: string) => void;
}

interface SelectProviderProps {
  value: SelectProps | SelectPropsWithValue;
}

const SelectContext = createContext<SelectContext>({
  isOpened: false,
  inScope: false,
  selectedValue: '',
  openHandler() {},
  changeValue() {},
});

SelectContext.displayName = 'Select';

export const useSelectContext = () => useContextInScope(SelectContext);

const SelectProvider = (props: PropsWithChildren<SelectProviderProps>) => {
  const {
    value: { defaultValue, onOpenChange },
    children,
  } = props;
  const { value, changeValue } = useSelectValue(defaultValue);
  const { isOpened, openSelect, closeSelect } = useSelect(false);
  let composedSelectValue = value;

  if ('value' in props.value) {
    const { value: valueProps } = props.value;

    composedSelectValue = valueProps ?? value;
  }

  const openHandler = () => {
    isOpened ? closeSelect() : openSelect();
  };

  const memoizedValue = useMemo(
    () => ({
      selectedValue: composedSelectValue,
      inScope: true,
      isOpened,
      openHandler,
      changeValue,
    }),
    [composedSelectValue, isOpened],
  );

  useEffect(() => {
    onOpenChange?.(isOpened);
  }, [isOpened]);

  useEffect(() => {
    if ('onValueChange' in props.value) {
      const { onValueChange } = props.value;

      composedSelectValue && onValueChange(composedSelectValue);
    }
  }, [composedSelectValue]);

  return <SelectContext.Provider value={memoizedValue}>{children}</SelectContext.Provider>;
};

const useSelectValue = (defaultValue: SelectProps['defaultValue']) => {
  const [value, setValue] = useState(defaultValue ?? '');

  const changeValue = (selectValue: typeof value) => setValue(selectValue);

  return { value, changeValue } as const;
};

const useSelect = (opened = false) => {
  const [isOpened, openSelect, closeSelect] = useBoolean(opened);

  const closeWithEscape = useCallback((e: KeyboardEvent) => {
    if (e.key === 'Escape') closeSelect();
  }, []);

  useEffect(() => {
    isOpened
      ? document.addEventListener('keydown', closeWithEscape)
      : document.removeEventListener('keydown', closeWithEscape);

    return () => document.removeEventListener('keydown', closeWithEscape);
  }, [isOpened]);

  return { isOpened, openSelect, closeSelect, closeWithEscape };
};

export default SelectProvider;
