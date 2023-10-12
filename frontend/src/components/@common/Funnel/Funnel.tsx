import { Children, isValidElement, ReactElement, ReactNode, useEffect } from 'react';

import { useToast } from '@/context/Toast/ToastContext';
import { NonEmptyArray } from '@/types/common/utility';

export interface FunnelProps<Steps extends NonEmptyArray<string>> {
  steps: Steps;
  step: Steps[number];
  children: Array<ReactElement<StepProps<Steps>>> | ReactElement<StepProps<Steps>>;
}

export interface StepProps<Steps extends NonEmptyArray<string>> {
  name: Steps[number];
  onNext?: () => void;
  children?: ReactNode;
}

export const Funnel = <Steps extends NonEmptyArray<string>>(funnelProps: FunnelProps<Steps>) => {
  const { steps, step, children } = funnelProps;
  const { toast } = useToast();
  const validChildren = Children.toArray(children)
    .filter(isValidElement)
    .filter(i => steps.includes((i.props as Partial<StepProps<Steps>>).name ?? '')) as Array<
    ReactElement<StepProps<Steps>>
  >;

  const targetStep = validChildren.find(child => child.props.name === step);

  if (!targetStep) {
    toast.warning(`${step} 스텝 컴포넌트를 찾지 못했습니다.`);
    return null;
  }

  return targetStep;
};

export const Step = <T extends NonEmptyArray<string>>({ onNext, children }: StepProps<T>) => {
  useEffect(() => {
    onNext?.();
  }, [onNext]);

  return children;
};
