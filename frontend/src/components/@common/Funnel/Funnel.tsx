import { Children, isValidElement, PropsWithChildren, ReactElement, useEffect } from 'react';

import { NonEmptyArray } from '@/types/common/utility';
import { RuntimeError } from '@/utils/errors';

export interface FunnelProps<Steps extends NonEmptyArray<string>> {
  steps: Steps;
  step: Steps[number];
  children: Array<ReactElement<StepProps<Steps>>> | ReactElement<StepProps<Steps>>;
}

export interface StepProps<Steps extends NonEmptyArray<string>> extends PropsWithChildren {
  name: Steps[number];
  onNext?: VoidFunction;
}

export const Funnel = <Steps extends NonEmptyArray<string>>(props: FunnelProps<Steps>) => {
  const { steps, step, children } = props;
  const validChildren = Children.toArray(children)
    .filter(isValidElement<StepProps<Steps>>)
    .filter(({ props }) => steps.includes(props.name));

  const targetStep = validChildren.find(child => child.props.name === step);

  if (!targetStep) {
    throw new RuntimeError(
      { code: 'WRONG_URL_FORMAT' },
      `${step} 스텝 컴포넌트를 찾지 못했습니다.`,
    );
  }

  return targetStep;
};

export const Step = <T extends NonEmptyArray<string>>({ onNext, children }: StepProps<T>) => {
  useEffect(() => {
    onNext?.();
  }, [onNext]);

  return children;
};
